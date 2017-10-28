import json

from flask import Flask, jsonify, request

app = Flask(__name__)

@app.route('/log', methods=['POST'])
def post(log):
    print(request.data)

if __name__ == '__main__':
    app.run(host = '0.0.0.0', debug = True)
