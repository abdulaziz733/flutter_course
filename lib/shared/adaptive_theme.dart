import 'package:flutter/material.dart';

final ThemeData _androidTheme = ThemeData(
  brightness: Brightness.light,
  primarySwatch: Colors.deepOrange,
  accentColor: Colors.deepPurple,
  fontFamily: 'Oswald',
  buttonColor: Colors.deepPurple,
);

final ThemeData _iosTheme = ThemeData(
  brightness: Brightness.light,
  primarySwatch: Colors.grey,
  accentColor: Colors.blue,
  fontFamily: 'Oswald',
  buttonColor: Colors.blue,
);

ThemeData getAdaptiveTheme(context) {
  return Theme.of(context).platform == TargetPlatform.android
      ? _androidTheme
      : _iosTheme;
}
