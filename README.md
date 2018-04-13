# 高仿饿了么加减View

## 效果


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