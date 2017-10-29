#!/usr/bin/env python
# -*- coding: utf-8 -*-

import json
import requests
import urllib
import urllib2
from datetime import datetime

URL = "http://34.237.68.65/user"

dic = {'status': 0, 'memo': '', 'nameid': 0, 'time': ''}
dic['time'] = datetime.now().strftime("%Y/%m/%d %H:%M:%S")
jsonstring = json.dumps(dic)
print(jsonstring)
response = requests.post(URL, data=jsonstring)
print(response.status_code)    # HTTPのステータスコード取得
print(response.text)    # レスポンスのHTMLを文字列で取得
