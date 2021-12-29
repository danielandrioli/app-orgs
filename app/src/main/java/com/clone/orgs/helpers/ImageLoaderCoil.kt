package com.clone.orgs.helpers

import android.content.Context
import android.os.Build
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.clone.orgs.R

class ImageLoaderCoil {

    //O imageloader, dessa maneira, nem precisa ser enviado como parâmetro no método load da imageview.
    fun configuraImageLoaderCoil(context: Context){ //Singleton ImageLoader
        Coil.setImageLoader(ImageLoader.Builder(context)
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder(context))
                } else {
                    add(GifDecoder())
                }
            }
            .error(R.drawable.error)
            .placeholder(R.drawable.fundo_placeholder)
            .build())
    }
}