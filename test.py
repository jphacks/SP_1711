#!/usr/bin/env python
# -*- coding: utf-8 -*-
'''
import urllib
import urllib2

url = "http://34.237.68.65/user"
params = {"id": 0, "param": "dammy"}
params = urllib.urlencode(params)

req = urllib2.Request(url)
# ヘッダ設定
req.add_header('test', 'application/x-www-form-urlencoded')
# パラメータ設定
req.add_data(params)


res = urllib2.urlopen(req)
r = res.read()
print r
'''

#!/usr/bin/env python
# -*- coding: utf-8 -*-

import requests

url = "http://34.237.68.65/user"
s = requests.session()
params = {"id": 0, "param": "dammy"}
r = s.post(url, data=params)
print r.text.encode("utf-8")
