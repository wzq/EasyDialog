# EasyDialog
A custom dialog.


# Usage

```groovy
compile 'com.wzq.easydialog:library:1.0.0'
```

Change loading progress colors or dialog theme.

```java
EasyDialog.init([theme id], new CircularProgressDrawable
                                    .Builder(this)
                                    .colors(getResources().getIntArray(R.array.gplus_colors))
                                    .sweepSpeed(1f)
                                    .strokeWidth(mStrokeWidth)
                                    .style(CircularProgressDrawable.Style.ROUNDED)
                                    .build());
```

About [CircularProgressDrawable](https://github.com/castorflex/SmoothProgressBar)