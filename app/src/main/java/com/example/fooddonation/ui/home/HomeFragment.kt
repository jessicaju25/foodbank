package com.example.fooddonation.ui.home
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fooddonation.R


class HomeFragment : Fragment() {
    private var webView: WebView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadMap()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        webView = view.findViewById(R.id.webView)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView = null
    }

    fun loadMap() {
        webView?.let { webView ->
            webView.settings.javaScriptEnabled = true
            webView.settings.setSupportZoom(false) // Disable zooming
            webView.settings.displayZoomControls = false // Hide zoom controls
            webView.settings.builtInZoomControls = false
            webView.settings.setGeolocationEnabled(false) // Disable geolocation
            webView.settings.setSupportMultipleWindows(false) // Disable multiple windows

            webView.webViewClient = object : WebViewClient() {
                override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                    // Handle the error here and display an error message
                    Toast.makeText(requireContext(), "Failed to load website", Toast.LENGTH_SHORT).show()
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    // Page finished loading
                    // Inject JavaScript to remove the menu bar, navbar, top bar, and footer elements
                    webView.evaluateJavascript(
                        "(function() { " +
                                "   var menuBar = document.querySelector('.breadcrumb'); " +
                                "   if (menuBar) menuBar.remove(); " +
                                "   var navbarWrap = document.querySelector('div.navbar-wrap'); " +
                                "   if (navbarWrap) navbarWrap.remove(); " +
                                "   var contentContainer = document.querySelector('div.content-container'); " +
                                "   if (contentContainer) contentContainer.style.marginTop = '0'; " +
                                "   var topBar = document.querySelector('div.top-bar'); " +
                                "   if (topBar) topBar.remove(); " +
                                "   var footerCon = document.querySelector('div.footer-con'); " +
                                "   if (footerCon) { " +
                                "       footerCon.remove(); " +
                                "   } " +
//                                "   var footer = document.querySelector('footer.footer-wrap'); " +
//                                "   if (footer) footer.remove(); " +
                                "   var thElements = document.querySelectorAll('th[scope=\"col\"]'); " +
                                "   if (thElements) { " +
                                "       for (var i = 0; i < thElements.length; i++) { " +
                                "           var thElement = thElements[i]; " +
                                "           if (thElement.textContent === '대표자' || thElement.textContent === '번호') { " +
                                "               thElement.remove(); " +
                                "           } " +
                                "       } " +
                                "   } " +
                                "   var tdNumElements = document.querySelectorAll('td.num'); " +
                                "   if (tdNumElements) { " +
                                "       for (var i = 0; i < tdNumElements.length; i++) { " +
                                "           tdNumElements[i].remove(); " +
                                "       } " +
                                "   } " +
                                "   var tdHitsElements = document.querySelectorAll('td.hits:not(:has(span))'); " +
                                "   if (tdHitsElements) { " +
                                "       for (var i = 0; i < tdHitsElements.length; i++) { " +
                                "           tdHitsElements[i].remove(); " +
                                "       } " +
                                "   } " +
                                "   var mapArea = document.querySelector('div.map-area'); " +
                                "   var footer = document.querySelector('footer.footer-wrap'); " +
                                "   if (mapArea && footer) { " +
                                "       footer.parentNode.insertBefore(mapArea, footer); " +
                                "   } " +
                                "})();",
                        null
                    )

                    // You can perform actions here or display a message indicating successful loading.
                    Toast.makeText(requireContext(), "Website loaded successfully", Toast.LENGTH_SHORT).show()
                }
            }

            webView.loadUrl("https://www.foodbank1377.org/introduce/foodbankMap.do")
        }
    }
}
