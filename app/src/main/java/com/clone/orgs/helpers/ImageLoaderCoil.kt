package com.clone.orgs.helpers

import android.content.Context
import android.os.Build
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.clone.orgs.R

class ImageLoaderCoil {
    //Configurando o imageloader dessa maneira, nem precisa enviar a property como parâmetro no método load da imageview.
    companion object{
        private lateinit var imgLoader: ImageLoader
        fun getAndSetImageLoaderCoil(context: Context): ImageLoader{ //Singleton ImageLoader
            if(::imgLoader.isInitialized) return imgLoader
            imgLoader = ImageLoader.Builder(context)
                .componentRegistry {
                    if (Build.VERSION.SDK_INT >= 28) {
                        add(ImageDecoderDecoder(context))
                    } else {
                        add(GifDecoder())
                    }
                }
                .error(R.drawable.error)
                .placeholder(R.drawable.fundo_placeholder)
                .build()
            Coil.setImageLoader(imgLoader)
            return imgLoader
        }
    }
}