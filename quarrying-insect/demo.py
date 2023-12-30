import cv2
import khandy
import requests
import numpy as np
# from tkinter import Label, Button, Entry, filedialog, Frame, StringVar
# from tkinterdnd2 import TkinterDnD, DND_FILES

from insectid import InsectDetector
from insectid import InsectIdentifier

# 加载图片
def load_image(input_source):
    # 如果输入的图片是url格式
    if input_source.startswith(('http:', 'https:')):
        response = requests.get(input_source)
        image_array = np.asarray(bytearray(response.content), dtype=np.uint8)
        image = cv2.imdecode(image_array, cv2.IMREAD_COLOR)
    else:
        input_source = input_source.replace('{', '').replace('}', '')
        # 如果输入的图片路径最外层有{}，则去掉
        image = cv2.imread(input_source)
    return image

# 识别
def main(input_source):
    detector = InsectDetector()
    identifier = InsectIdentifier()

    image = load_image(input_source)

    if image is None:
        print("Error loading the image.")
        return

    image_for_draw = image.copy()
    image_height, image_width = image.shape[:2]

    # boxes昆虫框选，confs置信度，classes类别指数
    boxes, confs, classes = detector.detect(image)

    for box, conf, class_ind in zip(boxes, confs, classes):
        box = box.astype(np.int32)
        box_width = box[2] - box[0] + 1
        box_height = box[3] - box[1] + 1
        if box_width < 30 or box_height < 30:
            continue

        cropped = khandy.crop(image, box[0], box[1], box[2], box[3])
        results = identifier.identify(cropped)  # 裁剪图片并识别
        print(results[0])
        prob = results[0]['probability']
        if prob < 0.10:  # 识别不出
            text = 'Unknown'
        else:  # 结果和准确率
            text = '{}: {:.3f}'.format(results[0]['chinese_name'], results[0]['probability'])
        position = [box[0] + 2, box[1] - 20]
        position[0] = min(max(position[0], 0), image_width)
        position[1] = min(max(position[1], 0), image_height)
        cv2.rectangle(image_for_draw, (box[0], box[1]), (box[2], box[3]), (0, 255, 0), 2)  # 框选出昆虫
        image_for_draw = khandy.draw_text(image_for_draw, text, position,
                                          font='simsun.ttc', font_size=15)

    # 展示识别结果
    cv2.imshow('image', image_for_draw)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

# class ImageProcessorApp:
#     def __init__(self, master):
#         self.master = master
#         master.title("识别昆虫")
#
#         self.main_frame = Frame(master, padx=20, pady=20)
#         self.main_frame.pack()
#
#         self.label = Label(self.main_frame, text="输入图片链接或者拖动图片到此:", font=("Helvetica", 12))
#         self.label.grid(row=0, column=0, columnspan=2, pady=(0, 10))
#
#         self.entry_var = StringVar()
#         self.entry = Entry(self.main_frame, textvariable=self.entry_var, width=40, font=("Helvetica", 10))
#         self.entry.grid(row=1, column=0, columnspan=2, pady=(0, 10))
#
#         self.browse_button = Button(self.main_frame, text="选择文件", command=self.browse_file, font=("Helvetica", 10))
#         self.browse_button.grid(row=2, column=0, pady=(0, 10))
#
#         self.process_button = Button(self.main_frame, text="识别", command=self.process_image,
#                                      font=("Helvetica", 10))
#         self.process_button.grid(row=2, column=1, pady=(0, 10))
#
#         self.detector = InsectDetector()
#         self.identifier = InsectIdentifier()
#
#         # 拖拽图片
#         master.drop_target_register(DND_FILES)
#         master.dnd_bind('<<Drop>>', self.drop)
#
#     def browse_file(self):  # 输入图片
#         file_path = filedialog.askopenfilename()
#         self.entry_var.set(file_path)
#
#     def process_image(self):
#         input_source = self.entry_var.get()
#         image = load_image(input_source)
#
#         if image is None:
#             print("Error loading the image.")
#             return
#         # 识别
#         main(input_source)
#
#     def drop(self, event):
#         file_path = event.data
#         self.entry_var.set(file_path)


if __name__ == '__main__':
    img_path = r'https://ts1.cn.mm.bing.net/th/id/R-C.45cc60b05479feb1384a4a95b0da8ec0?rik=wk5wR2wUgSY%2fow&riu=http%3a%2f%2fmuseum.ioz.ac.cn%2fupload%2fzhuanti%2fhebeidiaochaheshibie%2fbig%2f790002.jpg&ehk=a9fl%2fO0wQbTCpfa%2b0Vrxk1FeiNFJoKtqQGAudAdjTQI%3d&risl=&pid=ImgRaw&r=0'
    main(img_path)
    # root = TkinterDnD.Tk()
    # app = ImageProcessorApp(root)
    # root.mainloop()