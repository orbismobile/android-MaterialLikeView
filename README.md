<h1 align="center">
    <br>
    <a href="https://github.com/orbismobile">
    <img src="Screenshots/ic_launcher_favorite_web.png" alt="MaterialLikeView" width="200">
    </a>
    <br>
    MaterialLikeView Widget 
    <br>
</h1>

`MaterialLikeView is a custom widget with a material design style, that allows you to like any view in Android 
with an awesome custom animation. 


## Usage

```xml
<com.orbis.materiallikeview.MaterialLikeView
        android:id="@+id/mlvCarlitos"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:mlvCircleViewColor="@color/md_amber_600"
        app:mlvFavoriteState="false"
        app:mlvFavoriteIcon="@drawable/ic_star_amber_600_48dp"
        app:mlvNotFavoriteIcon="@drawable/ic_star_border_grey_700_48dp"/>
```


## Key Features

+-------------------------+--------------------------------------------------------+
| Key                        | Description                                         |
+=======================--+========================================================+
| FavoriteIcon               | Arbitrary HTTP header, e.g. ``X-API-Token:123``.    |
| ``app:mlvFavoriteIcon``    |                                                     |
+-------------------------+--------------------------------------------------------+
| FavoriteState              | Appends the given name/value pair as a query        |
| ``app:mlvFavoriteState``         | string parameter to the URL.                  |
|                            | The ``==`` separator is used.                       |
+-------------------------+--------------------------------------------------------+
| CircleViewColor            | Request data fields to be serialized as a JSON      |
| ``app:mlvCircleViewColor`` | object (default), or to be form-encoded             |
|                            | (``--form, -f``).                                   |
+-------------------------+--------------------------------------------------------+
| DotViewColor1              | Useful when sending JSON and one or                 |
| ``app:mlvDotsViewColor1``  | more fields need to be a ``Boolean``                |
|                            | nested ``Object``, or an ``Array``,  e.g.,          |
|                            | (note the quotes).                                  |
+-------------------------+--------------------------------------------------------+
| DotViewColor2              | Only available with ``--form, -f``.                 |
| ``app:mlvDotsViewColor2``  | For example ``screenshot@~/Pictures/img.png``.      |
|                            | The presence of a file field results                |
+-------------------------+--------------------------------------------------------+                 
| DotViewColor3              | Only available with ``--form, -f``.                 |
| ``app:mlvDotsViewColor3``  | For example ``screenshot@~/Pictures/img.png``.      |
|                            | The presence of a file field results                |
+-------------------------+--------------------------------------------------------+   
| DotViewColor4              | Only available with ``--form, -f``.                 |
| ``app:mlvDotsViewColor4``  | For example ``screenshot@~/Pictures/img.png``.      |
|                            | The presence of a file field results                |
+-------------------------+--------------------------------------------------------+   
