from flask import Flask 
from flask_cors import CORS 

app = Flask(__name__)
CORS(app)

#importamos el archivo productos.py #Aquí se agregarían los demás archivos vía import en caso de tener otras API’s


