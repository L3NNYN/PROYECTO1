from flask import jsonify, request
from init import app

import tableroModel

@app.route('/insert_jugador',  methods=['POST'])
def insert_jugador():
    try:
        _json = request.get_json(force=True)
        json_items = []
        content = {}
        _nombre = _json['nombre']
        _color = _json['color']
        _fecha = _json['fecha']
        content = {'nombre': _nombre, 'color': _color, 'fecha': _fecha}
        
        tableroModel.insertarJugador(content) #se ingresan los datos al array
        print(tableroModel.getJugadores())
        json_items.append(content)
        content = {}

        return jsonify(json_items) #se retorna el formato JSON
    except Exception as e:
        print(e)
    finally:
        print(_json['nombre'])