package com.example.android.kfupmsocialspace.contract;

import com.example.android.kfupmsocialspace.model.Blog;

import java.util.List;

public class blogContract {

        public interface IView {

                void myblogs(List<Blog> myBlogsList);

        }

        public interface IPresenter {

        }

        public interface IModel {

        }
}

