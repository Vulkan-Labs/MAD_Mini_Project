package com.mad.miniproject_teamvulkan;

import java.util.List;

public interface ProductAddListner {
    void onProductLoadSuccess(List<products> productAddList);
    void onProductLoadFailed(String message);

}
