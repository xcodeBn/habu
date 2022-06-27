package com.example.habuonlineshop.Dialogues;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.core.text.HtmlCompat;

import com.example.habuonlineshop.R;

public class Privacypolictdialogue extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Dialog dialog;
    String policy =
            "<p> If you require any more information or have any questions about our privacy policy, please feel free to contact us by email at [CONTACT@HABU.COM].</p>\n" +
            "<p>At Habu@org we consider the privacy of our visitors to be extremely important. This privacy policy document describes in detail the types of personal information is collected and recorded by [YOUR SITE URL] and how we use it. </p>\n" +
            "<p> <b>Log Files</b><br /> Like many other Web sites, [YOUR SITE URL] makes use of log files. These files merely logs visitors to the site – usually a standard procedure for hosting companies and a part of hosting services's analytics. The information inside the log files includes internet protocol (IP) addresses, browser type, Internet Service Provider (ISP), date/time stamp, referring/exit pages, and possibly the number of clicks. This information is used to analyze trends, administer the site, track user's movement around the site, and gather demographic information. IP addresses, and other such information are not linked to any information that is personally identifiable. </p>\n" +
            "<p> <b>Cookies and Web Beacons</b><br />[YOUR SITE URL] uses cookies to store information about visitors' preferences, to record user-specific information on which pages the site visitor accesses or visits, and to personalize or customize our web page content based upon visitors' browser type or other information that the visitor sends via their browser. </p>\n" +
            "<p><b>DoubleClick DART Cookie</b><br /> </p>\n" +
            "<p>\n" +
            "→ Google, as a third party vendor, uses cookies to serve ads on [YOUR SITE URL].<br /><br />\n" +
            "→ Google's use of the DART cookie enables it to serve ads to our site's visitors based upon their visit to [YOUR SITE URL] and other sites on the Internet. <br /><br />\n" +
            "→ Users may opt out of the use of the DART cookie by visiting the Google ad and content network privacy policy at the following URL – <a href=\"http://www.google.com/privacy_ads.html\">http://www.google.com/privacy_ads.html</a> </p>\n" +
            "<p><b>Our Advertising Partners</b><br /> </p>\n" +
            "<p> Some of our advertising partners may use cookies and web beacons on our site. Our advertising partners include ……. <br /></p>\n" +
            "<ul>\n" +
            "<li>Google</li>\n" +
            "<li>Commission Junction</li>\n" +
            "<li>Amazon</li>\n" +
            "<li>Adbrite</li>\n" +
            "<li>Clickbank</li>\n" +
            "<li>Yahoo! Publisher Network</li>\n" +
            "<li>Chitika</li>\n" +
            "<li>Kontera</li>\n" +
            "</ul>\n" +
            "<p><em>While each of these advertising partners has their own Privacy Policy for their site, an updated and hyperlinked resource is maintained here: <a href=\"https://www.privacypolicyonline.com/privacy-policy-links/\">Privacy Policy Links</a>.<br /> <br />\n" +
            "You may consult this listing to find the privacy policy for each of the advertising partners of [YOUR SITE URL].</em></p>\n" +
            "<p> These third-party ad servers or ad networks use technology in their respective advertisements and links that appear on [YOUR SITE URL] and which are sent directly to your browser. They automatically receive your IP address when this occurs. Other technologies (such as cookies, JavaScript, or Web Beacons) may also be used by our site's third-party ad networks to measure the effectiveness of their advertising campaigns and/or to personalize the advertising content that you see on the site. </p>\n" +
            "<p> [YOUR SITE URL] has no access to or control over these cookies that are used by third-party advertisers. </p>\n" +
            "</p>\n" +
            "<p><b>Third Party Privacy Policies</b><br />\n" +
            "You should consult the respective privacy policies of these third-party ad servers for more detailed information on their practices as well as for instructions about how to opt-out of certain practices. [YOUR SITE URL]'s privacy policy does not apply to, and we cannot control the activities of, such other advertisers or web sites. You may find a comprehensive listing of these privacy policies and their links here: <a href=\"https://www.privacypolicyonline.com/privacy-policy-links/\" title=\"Privacy Policy Links\">Privacy Policy Links</a>.</p>\n" +
            "<p> If you wish to disable cookies, you may do so through your individual browser options. More detailed information about cookie management with specific web browsers can be found at the browsers' respective websites. <a href=\"https://www.privacypolicyonline.com/what-are-cookies/\" >What Are Cookies?</a></p>\n" +
            "<p><strong>Children's Information</strong><br />We believe it is important to provide added protection for children online. We encourage parents and guardians to spend time online with their children to observe, participate in and/or monitor and guide their online activity.<br />\n" +
            "[YOUR SITE URL] does not knowingly collect any personally identifiable information from children under the age of 13.  If a parent or guardian believes that [YOUR SITE URL] has in its database the personally-identifiable information of a child under the age of 13, please contact us immediately (using the contact in the first paragraph) and we will use our best efforts to promptly remove such information from our records.</p>\n" +
            "<p>\n" +
            "<b>Online Privacy Policy Only</b><br />\n" +
            "This privacy policy applies only to our online activities and is valid for visitors to our website and regarding information shared and/or collected there.<br />\n" +
            "This policy does not apply to any information collected offline or via channels other than this website.</p>\n" +
            "<p><b>Consent</b><br />\n" +
            "By using our website, you hereby consent to our privacy policy and agree to its terms.\n" +
            "</p>\n" +
            "<p><b>Update</b><br />This Privacy Policy was last updated on: Nov 1st, 2019. Should we update, amend or make any changes to our privacy policy, those changes will be posted here.</p>\n" +
            "<p><!-- END of Privacy Policy || Generated by http://www.PrivacyPolicyOnline.com || --></p>\n" +
            "<p>";

    public Privacypolictdialogue(Activity a){
        super(a);
        this.activity = a;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialogue_privacypolicy);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WebView t = findViewById(R.id.privacywebview);


        t.loadUrl("file:///android_asset/privacypolicy.html");

        TextView goback  = findViewById(R.id.gobackbtn);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
