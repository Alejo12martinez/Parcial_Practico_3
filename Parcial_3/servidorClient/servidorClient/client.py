import requests
from flask import Flask, request, jsonify

app = Flask(__name__)

@app.route('/make_request', methods=['GET', 'POST', 'PUT', 'DELETE'])
def make_request():
    # Obtener los datos del JSON de la solicitud
    request_data = request.get_json()

    # Verificar si el tipo de solicitud es válido
    if 'method' not in request_data or request_data['method'] not in ['GET', 'POST', 'PUT', 'DELETE']:
        return jsonify({'error': 'Invalid or missing request method'}), 400

    # Construir la URL completa
    url = f"http://192.168.60.3:5000{request_data['endpoint']}"

    # Enviar la solicitud al servidor Flask
    if request_data['method'] == 'GET':
        response = requests.get(url)
    elif request_data['method'] == 'POST':
        response = requests.post(url, json=request_data.get('data', {}))
    elif request_data['method'] == 'PUT':
        response = requests.put(url, json=request_data.get('data', {}))
    elif request_data['method'] == 'DELETE':
        response = requests.delete(url)

    # Devolver la respuesta del servidor Flask al cliente (Postman)
    try:
        response_json = response.json()
    except ValueError:  # Handle JSON decoding error for non-JSON responses
        response_json = None

    return jsonify(response_json), response.status_code

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5001)
