# 痴漢冤罪防止アプリ
# ー       watchMe

[![watchMe](https://github.com/jphacks/SP_1711/blob/master/static/title2017.png?raw=true)](https://www.youtube.com/watch?v=69jYd2lfRgI&feature=youtu.be)

## 製品概要
### 通勤 x Tech

### 背景（製品開発のきっかけ、課題等）

- 今回のプロダクトの開発に至った背景
近年、日本では電車内の痴漢冤罪が問題となっている。
痴漢冤罪は被害を受ける男性はもちろんのこと、
女性にとっても冤罪事件を起こしたくないという気持ちから痴漢摘発を躊躇してしまう点で問題である。
また、疑われた場合に客観的な証拠を示すことが出来ないため、正しい判断を下すことも難しい。
よって男女双方のために客観的なデータを示すことが必要であると考えた。

- 着目した顧客・顧客の課題・現状
車内において「いつ、どこで、何をしていたか」という客観的な情報を用意できないため、
痴漢か冤罪かの判断が難しい。


### 製品説明（具体的な製品の説明）
痴漢冤罪の無実を示す証拠をウェアラブルデバイスから取得し、
スマートフォンアプリ上で統合、可視化します。

### 特長

#### 1. 乗車中の腕の動きや筋電、GPSの情報を自動で取得

#### 2. 客観的なデータを元にいつ、どこで、何をしていたかを再現し、わかりやすく提示する

### 使い方

#### 1. 乗車前にデバイスを装着、アプリを起動

#### 2. 車中のモーション、位置情報をバックグラウンド上で記録

#### 3. 万が一、冤罪を疑われた際にはアプリ上で客観的な情報を提示

### 解決出来ること
痴漢を疑われた際に冤罪を証明をする客観的なデータを提示できる。

### 今後の展望
まわりの人に通知を送って目撃者を募れる。

## 開発内容・開発技術
### 活用した技術
#### API・データ
今回スポンサーから提供されたAPI、製品などの外部技術があれば記述をして下さい。
* Google Maps Android API
* Location API
* Speech Recognizer 
* AWS EC2
* AWS RDS

#### フレームワーク・ライブラリ・モジュール
* Unity
    * Final IK
    * Myo SDK
* Python 3
    * SQLAlchemy
    * PyMySQL
    * Flask

#### デバイス
* Android
* Myo

### 研究内容・事前開発プロダクト（任意）

* Unity上で人体モーションを再現
   * Myoから取得した９軸センサ情報と生体情報をUnity側に送信
   * センサ情報を基にモーションを生成
   * モーションデータを保存
   * 任意の時刻のモーションデータを再生
   * 任意の方向からモーションを確認可能
    

* Androidアプリケーションの機能
    * MySpeechRecognizer
        - 音声認識をトリガーとして機能間の遷移に利用
    * MyAndroidAPP
        - GPSと時刻の情報を5秒おきに取得しデータベースに保存
    * MyMapApplication
        - 時刻と位置情報をマップ上に可視化
        - 再生ボタンを押すとその時間のモーションを再生

* サーバーの機能
    * FlaskでAPIサーバーを構築
        * スマートフォンから送信されたデータをAPIでDBに格納
    * AWSによるサーバー構築
        * 高い堅牢性
        * 将来的に高いスケーラビリティ

![DB図](https://github.com/jphacks/SP_1711/blob/master/static/sp1711.png?raw=true)
