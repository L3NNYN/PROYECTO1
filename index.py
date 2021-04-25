from tkinter import *

raiz = Tk()

raiz.title("Cacao")

raiz.iconbitmap("Cacao.ico")

raiz.geometry("1200x750")

#Button(root, text = "Prueba", command = Hola).pack()

def DataCamp():
    Label(raiz, text="Wenas").pack()

button = Button(raiz, text="Hola", command = DataCamp).pack()



raiz.mainloop()
