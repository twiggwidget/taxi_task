package com.johngoodwin.taxi

import android.test.ActivityInstrumentationTestCase2
import android.widget.TextView
import junit.framework.Assert

class TaxiActivityTest extends ActivityInstrumentationTestCase2[TaxiActivity](classOf[TaxiActivity]) {
  def test1() {
    Assert.assertTrue(true)
  }

  def test2() {
    Assert.assertEquals("Hello. I'm Java !", getActivity.findViewById(R.id.scala_text_view).asInstanceOf[TextView].getText)
  }
}
