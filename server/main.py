import json
from flask import Flask, jsonify, request
import model

app = Flask(__name__)

@app.route('/')
def index():
    return 'Helloo World!'

@app.route('/user', methods=['POST'])
def postUser():
    json = request.json
    model.pushUser(
            json['email'],
            json['password']
            )
    return jsonify(res = 'ok')

@app.route('/log', methods=['POST'])
def postLog():
    json = request.json
    model.pushLog(
            json['user_id'],
            json['lat'],
            json['lng'],
            json['created_at']
            )
    return jsonify(res = 'ok')


if __name__ == '__main__':
    app.run(host = '0.0.0.0', debug = True)
