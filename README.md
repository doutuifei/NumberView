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

##### numView
* TranslationX:right->center
* Rotation:0->-360
* Alpha:0->1
* ScaleX:->0.1->1
* ScaleY:->0.1->1

#### 3.结束动画与开始相反

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
