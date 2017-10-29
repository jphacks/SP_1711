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
    print(json['xxx'])
    return jsonify(res = 'ok')

if __name__ == '__main__':
    app.run(host = '0.0.0.0', debug = True)
