/**
 * Created by kevin on 03/04/2016.
 */
package org.wecare.eliot

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView

class WeightActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight)
        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as ImageView?
        fab?.setOnClickListener(View.OnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show() })
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

}
