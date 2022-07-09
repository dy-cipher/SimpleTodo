package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import layout.TaskItemAdapter
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    // values (val) are variable that we never gonna change again


    var listOfTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object  : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
//                Remove the item from the list
                listOfTasks.removeAt(position)
//                notify the adapter that our dataset has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        loadItems()

        //Look up recyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recycleView to populate Items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        //Set up the button and input fiels, so that the user can enter a task and add it to the list
        val inputTextField = findViewById<EditText>(R.id.addTask)
        // get a reference to the button
        // and then set Onclick  Listenner

        findViewById<Button>(R.id.button).setOnClickListener {
            // Grab the text that the user enter

            val userInput = inputTextField.text.toString()

            //  Add the string to our list of tasks: listOfTasks

            listOfTasks.add(userInput)

            // Notify the adapter

            adapter.notifyItemInserted(listOfTasks.size -1)

            // Reset text field
            inputTextField.setText("")

            saveItems()


        }

    }

    // save the data that the has entered

    // Create a method to get the data file we need

    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    // Load the items by reading every line in the data file

    fun loadItems() {
        try {
            listOfTasks = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {

            ioException.printStackTrace()
        }


    }

    // Save items by writing them into our data file

    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

}