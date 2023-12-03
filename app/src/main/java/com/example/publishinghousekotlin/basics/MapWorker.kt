package com.example.publishinghousekotlin.basics

import android.webkit.WebChromeClient
import android.webkit.WebView

class MapWorker {

    companion object{
        private var apiKey = "094078ac-b2f8-43b8-a414-702a2e9f3ea8"
    }

    fun showAddress(address:String, webView: WebView){

        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webChromeClient = WebChromeClient()

        val htmlData = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="utf-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <script src="https://api-maps.yandex.ru/2.1/?apikey=$apiKey&lang=ru_RU" type="text/javascript"></script>
            </head>
            <body>
                <div id="map" style="width: 100%; height: 100vh;"></div>
                <script type="text/javascript">
                    function init() {
                        var myMap = new ymaps.Map('map', {
                            center: [55.74, 37.58],
                            zoom: 13,
                            controls: []
                        });

                        var searchControl = new ymaps.control.SearchControl({
                            options: {
                                provider: 'yandex#search'
                            }
                        });

                        myMap.controls.add(searchControl);

                        var address = "$address";
                        searchControl.search(address);
                    }

                    ymaps.ready(init);
                </script>
            </body>
            </html>
        """.trimIndent()

        webView.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8", null)
    }
}