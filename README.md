# 高仿饿了么加减View

## 效果
![img](https://github.com/mzyq/NumberView/blob/da2d4b2ffaa63122c39b2f9358ce3405e185004d/img/preview.gif)

[apk下载](https://fir.im/8js6)

## 分析
#### 1.初始addView、subView、numView都在父布局的right
```
 @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            view.layout(width - view.getMeasuredWidth(), height / 2 - view.getMeasuredHeight() / 2, width, height / 2 + view.getMeasuredHeight() / 2);
        }
    }
```
#### 2.开始动画

##### subView
* TranslationX:right->left
* Rotation:0->-360
* Alpha:0->1
* ScaleX:->0.1->1
* ScaleY:->0.1->1
```
 ObjectAnimator subTranslation = ObjectAnimator.ofFloat(btnSub, "TranslationX", 0, (width - btnSub.getMeasuredWidth()) * -1f);
        ObjectAnimator subRotation = ObjectAnimator.ofFloat(btnSub, "Rotation", 0, -360);
        ObjectAnimator subAlpha = ObjectAnimator.ofFloat(btnSub, "Alpha", 0, 1f);
        ObjectAnimator subScaleX = ObjectAnimator.ofFloat(btnSub, "ScaleX", 0.1f, 1f);
        ObjectAnimator subScaleY = ObjectAnimator.ofFloat(btnSub, "ScaleY", 0.1f, 1f);
```

##### numView
* TranslationX:right->center
* Rotation:0->-360
* Alpha:0->1
* ScaleX:->0.1->1
* ScaleY:->0.1->1
```
 ObjectAnimator numberTranslation = ObjectAnimator.ofFloat(textNumber, "TranslationX", 0, (width - textNumber.getMeasuredWidth()) / -2f);
        ObjectAnimator numberRotation = ObjectAnimator.ofFloat(textNumber, "Rotation", 0, -360);
        ObjectAnimator numberAlpha = ObjectAnimator.ofFloat(textNumber, "Alpha", 0, 1f);
        ObjectAnimator numberScaleX = ObjectAnimator.ofFloat(textNumber, "ScaleX", 0.1f, 1f);
        ObjectAnimator numberScaleY = ObjectAnimator.ofFloat(textNumber, "ScaleY", 0.1f, 1f);
```

#### 组合
```
 AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(numberTranslation, numberRotation, numberAlpha, numberScaleX, numberScaleY,
                subTranslation, subRotation, subAlpha, subScaleX, subScaleY);
        animatorSet.setDuration(duration);
        animatorSet.setStartDelay(startDelay);
        animatorSet.start();
```


#### 3.结束动画与开始相反
```
 btnSub.clearAnimation();
        ObjectAnimator subTranslation = ObjectAnimator.ofFloat(btnSub, "TranslationX", (width - btnSub.getMeasuredWidth()) * -1f, 0);
        ObjectAnimator subRotation = ObjectAnimator.ofFloat(btnSub, "Rotation", 360, 0);
        ObjectAnimator subAlpha = ObjectAnimator.ofFloat(btnSub, "Alpha", 1f, 0);
        ObjectAnimator subScaleX = ObjectAnimator.ofFloat(btnSub, "ScaleX", 1f, 0.1f);
        ObjectAnimator subScaleY = ObjectAnimator.ofFloat(btnSub, "ScaleY", 1f, 0.1f);

        textNumber.clearAnimation();
        ObjectAnimator numberTranslation = ObjectAnimator.ofFloat(textNumber, "TranslationX", (width - textNumber.getMeasuredWidth()) / -2f, 0);
        ObjectAnimator numberRotation = ObjectAnimator.ofFloat(textNumber, "Rotation", 360, 0);
        ObjectAnimator numberAlpha = ObjectAnimator.ofFloat(textNumber, "Alpha", 1f, 0);
        ObjectAnimator numberScaleX = ObjectAnimator.ofFloat(textNumber, "ScaleX", 1f, 0.1f);
        ObjectAnimator numberScaleY = ObjectAnimator.ofFloat(textNumber, "ScaleY", 1f, 0.1f);

        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(numberTranslation, numberRotation, numberAlpha, numberScaleX, numberScaleY,
                subTranslation, subRotation, subAlpha, subScaleX, subScaleY);
        animatorSet.setDuration(duration);
        animatorSet.setStartDelay(startDelay);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                textNumber.setText(String.valueOf(number));
                animatorSet.removeAllListeners();
            }
        });
```

## 方法
* 获取number
```public int getNumber()```
* 接口 isAdd: true代表点击+，false代表点击-，number为当前值
```
public void setListener(OnChangeListener listener) {
             this.listener = listener;
         }

         public interface OnChangeListener {
             void onChange(boolean isAdd, int number);
         }
```

## LICENSE
```
 Copyright [2018] [muzi 727784430@qq.com]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
