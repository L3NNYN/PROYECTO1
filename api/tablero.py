from flask import jsonify, request
from init import app

import tableroModel

@app.route('/get_data/')
def get_data():
    try:
        json_items = []
        content = {}
        content = {'jugadores': tableroModel.getJugadores()}
        print(content)
        json_items.append(content)
        content = {}

        return jsonify(json_items) #se retorna el formato JSON
    except Exception as e:
        print(e)
    finally:
        print('listo')