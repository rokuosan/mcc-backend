# Minecraft Console Controller - Backend

## Overview

Minecraft Console Controller (以下MCC)はブラウザからサーバーを管理するためのコンソールを提供します。

HTTP通信のみ可能なネットワークからでもサーバーを操作したいというニーズに合わせて開発しました。

また、MCCはWeb3層構造に似た構造を持っています。
このリポジトリでは、その中間にあたるWebAPIを提供します。

実際にMinecraftサーバーで稼働させるためにはMCC-Plugin が必要になります。

## Installation

### Requirement

- JDK 11以降

### Get started

このサーバーを動かすには2つの手段が存在します。

- ビルド済み実行ファイルを用いて起動する(推奨)
- gradleから起動する

### ビルド済み実行ファイルを用いる

このリポジトリから最新のReleaseを確認してダウンロードしてください。

その後、以下のコマンドから実行します。

```shell
$ java -jar ファイル名.jar
```

### gradle

まずはこのリポジトリをクローンします。

```shell
$ git clone https://github.com/rokuosan/mcc-backend.git
```

その後、以下のコマンドから起動します。

```shell
$ gradlew bootRun
```

## Documentation

### Endpoint

| エンドポイント            | メソッド | 説明                            |
|--------------------|------|-------------------------------|
| /api/player/list   | GET  | オンラインのプレイヤーを返します。             |
| /api/player/{uuid} | GET  | プレイヤーリストからUUIDをもとにプレイヤーを探します。 |
| /api/player/join   | POST | POSTするJSONにあるプレイヤーを追加します。     |
| /api/player/quit   | POST | オンラインリストからパラメータのプレイヤーを削除します。  |
| /api/server/memory | GET  | サーバーのメモリ状況を取得します。             |

### /api/player/list

プレイヤーのリストを返します。

パラメータには``offline``を指定することができ、Bool値を使用します。

以下のように使用することですべてのプレイヤーを表示することができます。

```
/api/player/list?offline=true
```

### /api/player

主にプレイヤーに関する内容を扱います。

### /api/player/{uuid}

GET: uuidと一致するUUIDをもつプレイヤーデータを返します。
POST: uuidと一致するUUIDをもつプレイヤー情報を書き換えます。

### /api/player/join

プラグインが使用します。

プレイヤーが参加した時にPOSTされます。

リクエストボディにはJSON形式のプレイヤーデータがあります。

### /api/player/quit

プラグインが使用します。

プレイヤーが切断したときにPOSTされます。

joinとは異なりリクエストボディには何もありませんが、パラメータにuuidを持ちます。

### /api/server

主にサーバー自体の情報を扱います。

### /api/server/memory

メモリ情報を取得します。