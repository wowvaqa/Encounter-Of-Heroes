package com.mygdx.eoh.defaultClasses;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.eoh.gameClasses.Options;

/**
 *
 * Created by v on 2016-10-04.
 */
public class DefaultCamera extends OrthographicCamera {
    //BoundingBox left, right, top, bottom = null;
    Vector3 lastPosition;

    int rows;
    int columns;

//    public DefaultCamera() {
//    }

    public DefaultCamera(float viewportWidth, float viewportHeight) {
        super(viewportWidth, viewportHeight);
    }

    public void setWorld(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }

//    public void setWorldBounds(int left, int bottom, int width, int height) {
//        int top = bottom + height;
//        int right = left + width;
//
//        this.left = new BoundingBox(new Vector3(left - 2, 0, 0), new Vector3(left - 1, top, 0));
//        this.right = new BoundingBox(new Vector3(right + 1, 0, 0), new Vector3(right + 2, top, 0));
//        this.top = new BoundingBox(new Vector3(0, top + 1, 0), new Vector3(right, top + 2, 0));
//        this.bottom = new BoundingBox(new Vector3(0, bottom - 1, 0), new Vector3(right, bottom - 2, 0));
//    }

    @Override
    public void translate(float x, float y) {
        lastPosition = new Vector3(position);
        super.translate(x, y);
    }

    public void translateSafe(float x, float y) {
        translate(x, y);
        update();
        ensureBounds();
        update();
    }

    public void ensureBounds() {
        if (position.x < 0)
            position.set(lastPosition);
        if (position.y < 0)
            position.set(lastPosition);
        if (position.x > Options.tileSize * columns) {
            position.set(lastPosition);
        }
        if (position.y > Options.tileSize * rows) {
            position.set(lastPosition);
        }
//        if (isInsideBounds()) {
//            position.set(lastPosition);
//        }
    }

//    private boolean isInsideBounds() {
//
//        if (frustum.boundsInFrustum(left) || frustum.boundsInFrustum(right) || frustum.boundsInFrustum(top) || frustum.boundsInFrustum(bottom)) {
//            return true;
//        }
//        return false;
//    }

    public void attemptZoom(float newZoom) {
        if (this.zoom + newZoom < 1f) {
            newZoom = 0;
        } else if (this.zoom + newZoom > 5f) {
            newZoom = 0;
        }
        this.zoom += newZoom;
        //this.snapCameraInView();
    }

//    private void snapCameraInView() {
//        float halfOfCurrentViewportWidth = ((viewportWidth * zoom) / 2f);
//        float halfOfCurrentViewportHeight = ((viewportHeight * zoom) / 2f);
//        Gdx.app.log("viewportWidth:" + viewportWidth, "");
//        Gdx.app.log("viewportHight:" + viewportHeight, "");
//        Gdx.app.log("positionX:" + position.x, "");
//        Gdx.app.log("positionY:" + position.y, "");
//        Gdx.app.log("HalfOfCurrentViewportWidth:" + halfOfCurrentViewportWidth, "");
//        Gdx.app.log("HalfOfCurrentViewportHeight:" + halfOfCurrentViewportHeight, "");
//
//        //Check the vertical camera.
//        if (position.x - halfOfCurrentViewportWidth < 0f) //Is going off the left side.
//        {
//            //Snap back.
//            float amountGoneOver = position.x - halfOfCurrentViewportWidth;
//            position.x += Math.abs(amountGoneOver);
//        } else if (position.x + halfOfCurrentViewportWidth > viewportWidth) {
//            //Snap back.
//            float amountGoneOver = (viewportWidth - (position.x + halfOfCurrentViewportWidth));
//            position.x -= Math.abs(amountGoneOver);
//        }
//
//        //Check the horizontal camera.
//        if (position.y + halfOfCurrentViewportHeight > viewportHeight) {
//            float amountGoneOver = (position.y + halfOfCurrentViewportHeight) - viewportHeight;
//            position.y -= Math.abs(amountGoneOver);
//        } else if (position.y - halfOfCurrentViewportHeight < 0f) {
//            float amountGoneOver = (position.y - halfOfCurrentViewportHeight);
//            position.y += Math.abs(amountGoneOver);
//        }
//    }
}
