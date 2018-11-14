import 'package:flutter/material.dart';
import 'dart:async';
import 'dart:io';
import 'package:flutter/services.dart';
import 'package:fb_share_plugin/fb_share_plugin.dart';
import 'package:media_picker/media_picker.dart';


void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  Future<File> _mediaFile;

  @override
  void initState() {
    super.initState();

  }

  void _onImageButtonPressed(ImageSource source) {

        _mediaFile = MediaPicker.pickVideo(source: source).then((File _file) {
          print("test this");
          print(_file.toString());
        });

  }



  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Container(
          child: Column(
            children: <Widget>[
              Center(
                child: RaisedButton(
                    child: Text("Share Button"),
                    onPressed: ()async {
                  await FbSharePlugin.shareContent("http://www.sheikhsoft.com");
                }),
              ),
              Center(child: RaisedButton(
                  child: Text("Open Video Intend"),
                  onPressed: (){
                    _onImageButtonPressed(ImageSource.gallery);
              }),),
            ],
          ),
        ),
      ),
    );
  }
}
