from flask import jsonify, request
from init import app

import tableroModel

@app.route('/insert_jugador',  methods=['POST'])
def insert_jugador():
    try:
        _json = request.get_json(force=True)
        json_items = []
        _jugador = _json['nombre']
        tableroModel.insertarJugador(_jugador)
        print(tableroModel.getJugadores())
        content = {'respuesta': 'asd'}
        json_items.append(content)
        content = {}

        return jsonify(json_items) #se retorna el formato JSON
    except Exception as e:
        print(e)
    finally:
        print(_json['nombre'])