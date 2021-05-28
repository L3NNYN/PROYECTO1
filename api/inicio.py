from flask import jsonify, request
from init import app

@app.route('/insert_usuario/')
def get_proveedores():
    try:
        _json = request.get_json(force=True)
        json_items = []
        content = {}
        json_items.append(content)
        content = {}

        return jsonify(json_items) #se retorna el formato JSON
    except Exception as e:
        print(e)
    finally:
        print(_json['usuario'])