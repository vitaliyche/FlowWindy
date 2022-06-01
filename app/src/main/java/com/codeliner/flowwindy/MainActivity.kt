package com.codeliner.flowwindy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.codeliner.flowwindy.databinding.ActivityMainBinding
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener { launchFlow() }
    }


    // пытался сделать массив Flow из полученного значения, не получилось
//    private fun launchFlow() {
//        val N = binding.editTextNumber.text.toString().toInt()
//        val myArray = Array(N) { i -> i }
//        val myFlow: kotlinx.coroutines.flow.Flow<Int> = flow { myArray }
//        lifecycleScope.launchWhenStarted {
//            myFlow.collect {
//                delay(500)
//                binding.textView.text = it.toString()
//            }
//        }
//    }


    //не работает
//    private fun launchFlow() {
//        val N = binding.editTextNumber.text.toString().toInt()
//        val _myFlow = MutableSharedFlow<Int>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
//        val myFlow: SharedFlow<Int> = _myFlow.asSharedFlow()
//        lifecycleScope.launch {
//            _myFlow.emitAll(
//                flow {
//                    emit(N)
//                }
//            )
//        }
//    }



    //не работает
//    private fun launchFlow() {
//        val N = binding.editTextNumber.text.toString().toInt()
//        val myFlow: Flow<Int> = flow<Int> {
//            delay(500)
//            binding.textView.text = this.toString()
//        }.shareIn(lifecycleScope, started = SharingStarted.Lazily, replay = 1)
//    }

    // уменьшил объем кода
    private fun launchFlow() {
        //val N = binding.editTextNumber.text.toString().toInt()
        //val myDelay = N * 100
        val myFlow: kotlinx.coroutines.flow.Flow<Int> = flowOf(1, 2, 3, 10)
        lifecycleScope.launchWhenStarted {
            myFlow.collect {
                delay(500)
                binding.textView.text = it.toString()
            }
        }
    }

    //запустил поток, выводятся значения из flow по очереди
//    private fun launchFlow() {
//        val N = binding.editTextNumber.text.toString().toInt()
//        val myDelay = N * 100
//        val myFlow: kotlinx.coroutines.flow.Flow<Int> = flowOf(1, 2, 3, 10)
//        lifecycleScope.launchWhenStarted {
//            myFlow
//                .onEach {
//                    delay(500)
//                    binding.textView.text = it.toString()
//                }
//                .collect()
//        }
//    }

//    private fun launchFlow() {
//        val N = binding.editTextNumber.text.toString().toInt()
//        for (i in 1..N) {
//            val flow: kotlinx.coroutines.flow.Flow<Int> = flowOf(i)
//            runBlocking {
//                flow
//                    .collect {
//                        delay(1000)
//                        binding.textView.text = it.toString()
//                    }
//            }
//        }
//    }

//    private fun launchFlow() = runBlocking {
//        val N = binding.editTextNumber.text.toString().toInt() //получить число, введенное в поле ввода
//        val flow: kotlinx.coroutines.flow.Flow<Int> = flowOf(1,2,3,10)
//        flow
//            .collect {
//                delay(1000)
//                binding.textView.text = it.toString()
//        }

    //val result = N+1 // емитит значение index + 1
    //binding.textView.text = result.toString() // результат вывести в текстовое поле

    // каждое обновление должно находиться на новой строчке.
    // Необходимо создать N Flow<Int>
    // после задержки в (index + 1) * 100, емитит значение index + 1
    // Результирующий Flow должен суммировать значения всех N Flow
    // Суммирующий Flow должен возвращать значение после обновления каждого из N Flow

}

// TODO: разработать Flow, который суммирует значения других Flow.
/* 1. OK UI - на экране должно быть 3 элемента.
 OK Поле ввода, кнопка запуска и текстовое поле для вывода информации.
 OK При нажатии на кнопку получаем N - число введенное в поле ввода
 и запускаем Flow.
 OK Результат работы нужно вывести в текстовое поле.
 Каждое обновление должно находиться на новой строчке.

2. Flow. Необходимо создать N Flow<Int>, каждый из которых после задержки в (index + 1) * 100, емитит значение index + 1.
 Т.е. Flow с индексом 0 с задержкой 100 эмитит значение 1, Flow с индексом 1 с задержкой 200 эмитит значение 2.

3. Суммирование. Результирующий Flow должен суммировать значения всех N Flow.
 Суммирующий Flow должен возвращать значение после обновления каждого из N Flow.

Пример.
 Введенное количество: 1
 Результат: 1

 Введенное количество: 2
 Результат: 1 3

 Введенное количество: 3
 Результат: 1 3 6

 Введенное количество: 10
 Результат: 1 3 6 10 15 21 28

Задание необходимо реализовать на языке Kotlin.
 Результатом выполнения задания должен быть .apk файл и исходный код. */