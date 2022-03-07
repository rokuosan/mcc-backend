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

| エンドポイント            | メソッド | 説明                            | パラメータ            |
|--------------------|------|-------------------------------|------------------|
| /api/player/list   | GET  | オンラインのプレイヤーを返します。             | offline: Boolean |
| /api/player/{uuid} | GET  | プレイヤーリストからUUIDをもとにプレイヤーを探します。 | N/A              |
| /api/player/join   | POST | POSTするJSONにあるプレイヤーを追加します。     | N/A              |
| /api/player/quit   | POST | オンラインリストからパラメータのプレイヤーを削除します。  | uuid: String     |
| /api/server/memory | GET  | Return memory status          | N/A              |



