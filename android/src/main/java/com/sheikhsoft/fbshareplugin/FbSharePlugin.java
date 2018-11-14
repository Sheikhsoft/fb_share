package com.sheikhsoft.fbshareplugin;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** FbSharePlugin */
public class FbSharePlugin implements MethodCallHandler {

  CallbackManager callbackManager;
  ShareDialog shareDialog;

  private static final int REQUEST_VIDEO_CODE = 1000;
  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "fb_share_plugin");
    FacebookSdk.sdkInitialize(registrar.activeContext());


    channel.setMethodCallHandler(new FbSharePlugin(registrar));


  }
  private final Registrar mRegistrar;

  public FbSharePlugin(Registrar registrar) {
    this.mRegistrar = registrar;


  }

  @Override
  public void onMethodCall(MethodCall call, Result result) {
    callbackManager = CallbackManager.Factory.create();
    shareDialog = new ShareDialog(mRegistrar.activity());

    if (call.method.equals("shareContent")) {
      String contentUrl = (String) call.argument("videoUrl");

      Toast.makeText(mRegistrar.activity(), contentUrl, Toast.LENGTH_SHORT).show();

      fbShareContent(contentUrl);

    }
    else if (call.method.equals("shareVideo")) {
      String videoPath = (String) call.argument("videoPath");

      FbVideoShare(videoPath);
    }
    else if (call.method.equals("getPlatformVersion")) {
      result.success("Android flutter working" );
    } else {
      result.notImplemented();
    }
  }

  private void FbVideoShare(String videoPath) {

    Uri selectedVideo = Uri.parse(videoPath);
    Log.d("svideo",selectedVideo.toString());

    ShareVideo video = new ShareVideo.Builder()
            .setLocalUrl(selectedVideo)
            .build();
    Log.d("svideo",video.toString());

    ShareVideoContent videoContent = new ShareVideoContent.Builder()
            .setContentTitle("This Is Useful video ")
            .setContentDescription("Funny Video Form Youtube")
            .setVideo(video)
            .build();

    if (ShareDialog.canShow(ShareVideoContent.class)){
      shareDialog.show(videoContent);
    }
  }


  private void fbShareContent(String contentUrl) {
    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
      @Override
      public void onSuccess(Sharer.Result result) {
        Toast.makeText(mRegistrar.context(), "Share Successful", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onCancel() {
        Toast.makeText(mRegistrar.context(), "Share Cancel", Toast.LENGTH_SHORT).show();
      }

      @Override
      public void onError(FacebookException error) {
        Toast.makeText(mRegistrar.context(), error.getMessage(), Toast.LENGTH_SHORT).show();

      }
    });

    ShareLinkContent linkContent = new ShareLinkContent.Builder()
            .setQuote("SHEIKH SOFT")
            .setContentUrl(Uri.parse(contentUrl))
            .build();
    if (ShareDialog.canShow(ShareLinkContent.class)){
      shareDialog.show(linkContent);
    }
  }
}
