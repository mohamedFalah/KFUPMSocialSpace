package com.example.android.kfupmsocialspace;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import gun0912.tedbottompicker.TedBottomPicker;

public class AddImageWithCaptionFragment extends Fragment implements ImageWithCaptionListener {

    private ArrayList<ImgCap> imgCapArrayList = new ArrayList<>();
    private PerfectAdapter adapter;
    private RecyclerView recyclerView;
    private ImageView select, mainStream;
    private EditText captionEt;
    private int mCurrentPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.add_img_with_cap_layout, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        select = (ImageView) view.findViewById(R.id.selected_photo);
        mainStream = (ImageView) view.findViewById(R.id.currentStreamImage);
        captionEt = (EditText) view.findViewById(R.id.caption);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(getActivity())
                        .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                            @Override
                            public void onImagesSelected(ArrayList<Uri> uriList) {

                                imgCapArrayList.clear();
                                for (int i = 0; i < uriList.size(); i++) {
                                    ImgCap imgCap = new ImgCap(i, "", uriList.get(i));
                                    imgCapArrayList.add(imgCap);
                                }

                                adapter = new PerfectAdapter(getActivity(), imgCapArrayList, mainStream, AddImageWithCaptionFragment.this);
                                LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                recyclerView.setLayoutManager(mLayoutManager);
                                recyclerView.setAdapter(adapter);
                            }
                        })
                        .setPeekHeight(1600)
                        .showTitle(false)
                        .setCompleteButtonText("Done")
                        .setEmptySelectionText("No Select")
                        .create();

                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager());

            }
        });

        captionEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                imgCapArrayList.get(mCurrentPosition).setCaption(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void imgCaptionCallBack(int position) {
        mCurrentPosition = position;
        captionEt.setText(imgCapArrayList.get(mCurrentPosition).getCaption());
    }
}