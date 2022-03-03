package com.neppplus.lottosimulator_20220303

import android.icu.text.NumberFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

//    내 번호 6개 저장
//    코틀린은 단순 배열 초기화 int[] arr = {}; 문법 지원 x

//    숫자 목록을 파라미터로 넣으면 > Array로 만들어주는 함수 실행
    val mMyNumbers = arrayOf(13, 17, 20 , 31, 7 , 41)


//   컴퓨터가 뽑은 당첨번호 6개를 저장할 ArrayList
    val mWinNumberList = ArrayList<Int>()
    var mBonusNum = 0; // 보너스 번호는, 매 판마다 새로 뽑아야 함. 변경 소지 0, 화면이 어딘지는 줄 필요 x, 바로 대입 var

//   당첨번호를 보여줄 6개의 텍스트뷰를 담아둘 ArrayList
    val mWinNumTextViewList = ArrayList<TextView>()


//    사용한 금액, 당첨된 금액 합산 변수
    var mUseMoney = 0
    var mEarnMoney = 0L // 30억 이상의 당첨 대비. Long 타입으로 설정.


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    private fun setupEvents() {

        btnBuyLotto.setOnClickListener {

            buyLotto()

        }

    }

    private fun buyLotto(){

//        사용한 금액 늘려주기
        mUseMoney += 1000


//        6개의 당첨번호
//        코틀린의 for문은, for-each문법으로 기반.

//        ArrayList는 목록을 계속 누적 가능.
//        당첨번호 뽑기 전에, 기존의 당첨번호는 전부 삭제하고 다시 뽑자.

        mWinNumberList.clear()

        for ( i in 0 until 6  ) {

//            괜찮은 번호가 나올 때 까지 무한 반복
            while (true){

//                1~45의 랜덤 숫자
//                Math.random()은 0~1 => 1 ~ 45.xxx 로 가공 => Int로 캐스팅
                val randomNum = (Math.random() * 45 + 1).toInt()

//                중복 검사 통과 시 while 깨자
                if( !mWinNumberList.contains(randomNum) ) {

//                    당첨번호로, 뽑은 랜덤 숫자 등록
                    mWinNumberList.add( randomNum )

                    break
                }

            }

        }

//        만들어진 당첨번호 6개를 -> 작은수 ~ 큰 수로 정리해서 -> 텍스트뷰에 표현
        mWinNumberList.sort() // 자바로 직접 짜던 로직을 > 객체지향의 특성, 만들어져 있는 기능 활용으로 대체

        Log.d("당첨번호", mWinNumberList.toString())

//        for -> 돌면서, 당첨번호도 / 몇번째 바퀴인지도 필요 => 텍스트뷰를 찾아내야함.
        mWinNumberList.forEachIndexed { index, winNum ->

//            순서에 맞는 텍스트뷰 추출 => 문구로 당첨번호 설정
            mWinNumTextViewList[index].text = winNum.toString()

        }


//        보너스 번호 생성 -> 1~45 중 하나 , 당첨번호와 겹치지 않게.
        while (true){

            val ramdomNum = (Math.random() * 45 + 1).toInt()

            if (!mWinNumberList.contains(ramdomNum)){
//                겹치지 않는 숫자 뽑음.
                mBonusNum = ramdomNum
                break
            }

        }

//        텍스트뷰 배치
        txtBonusNum.text = mBonusNum.toString()

//        내숫자 6개와 비교, 등수 판정
        checkLottoRank()

    }

    private fun checkLottoRank () {


//        임시 당첨번호
//        mWinNumberList.clear()
//        mWinNumberList.add(13)
//        mWinNumberList.add(17)
//        mWinNumberList.add(20)
//        mWinNumberList.add(31)
//        mWinNumberList.add(7)
//        mWinNumberList.add(41)


//        내 번호 목록 / 당첨번호 목록 중, 같은 숫자가 몇개?
        var correctCount = 0;

//        내 번호를 하나씩 조회
        for (myNum in mMyNumbers){

//            당첨번호를 맞췄는가?  => 당첨번호 목록에 내 번호가 들어있나?

            if (mWinNumberList.contains(myNum)){
                correctCount++
            }

        }

//        맞춘 개수에 따른 등수 판정
        when(correctCount){
            6 -> {
                mEarnMoney += 3000000000
            }
            5 -> {

//                보너스 번호를 맞췄는지 ? => 보너스번호가 내 번호 목록에 들어있나?
                if (mMyNumbers.contains(mBonusNum)){
                    mEarnMoney += 50000000
                }
                else{
                    mEarnMoney += 2000000
                }

            }
            4 -> {
                mEarnMoney += 50000
            }
            3 -> {
//                5등 -> 5천원을 사용한 돈을 줄여주자.
                mUseMoney -= 5000
            }

        }

//        사용 금액  / 당첨금액을 텍스트뷰에 각각 반영
        txtUsedMoney.text = "${NumberFormat.getInstance().format(mUseMoney)}원"
        txtEarnMoney.text = "${NumberFormat.getInstance().format(mEarnMoney)}원"


    }


    private fun setValues() {

        mWinNumTextViewList.add(txtWinNum01)
        mWinNumTextViewList.add(txtWinNum02)
        mWinNumTextViewList.add(txtWinNum03)
        mWinNumTextViewList.add(txtWinNum04)
        mWinNumTextViewList.add(txtWinNum05)
        mWinNumTextViewList.add(txtWinNum06)

    }

}