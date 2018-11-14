import 'dart:async';

import 'package:flutter/services.dart';

class FbSharePlugin {
  static const MethodChannel _channel =
      const MethodChannel('fb_share_plugin');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<void> shareContent(String url) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'videoUrl': url,
    };
    await _channel.invokeMethod('shareContent',params);

  }

  static Future<void> shareVideo(String videoPath) async {
    final Map<String, dynamic> params = <String, dynamic>{
      'videoPath': videoPath,
    };
    await _channel.invokeMethod('shareContent',params);

  }


}
