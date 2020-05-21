package com.example.fyptest;

import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import static junit.framework.TestCase.assertNotNull;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.O_MR1)
public class RobolectricHomeActivityTest {

    private HomeActivity activity;

    @Before
    public void setUp() throws Exception
    {
        activity = Robolectric.buildActivity( HomeActivity.class )
                .create()
                .resume()
                .get();
    }



    @Test
    public void shouldHaveNavBarFragment() throws Exception
    {
        assertNotNull( activity.getFragmentManager().findFragmentById( R.id.nav_host_fragment));
    }


}
