package com.example.sipentas.provider;

import androidx.core.content.FileProvider;

import com.example.sipentas.R;

public class SipentasFileProvider extends FileProvider {
    public SipentasFileProvider() {
        super(R.xml.path);
    }
}

