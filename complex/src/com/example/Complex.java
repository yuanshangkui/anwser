package com.example;

/**
 * @author: yuansk
 * @date: 2021/1/26 / 17:16
 * @description: 复数类
 * 复数表示： a + bi，
 * 属性:
 * 实部a ，虚部b，均为整数
 *
 * 构造器：
 * 无参构造器：默认a=0,b=0
 * 有参构造器：1.参数为a,b
 *           2.参数为一个复数，要求new出的复数和传入的复数a,b相同
 *
 * 方法：
 * 复数加法 add
 * 减法 minus
 * 乘法 multiply
 * 获取实部 getRealPart
 * 获取虚部 getImaginaryPart
 *
 * 重写方法：
 * toString: 要求显示的字符串为 a+bi形式 ，如 -1+2i, 1-2i
 * equals：a,b相等时返回true
 *
 */
public class Complex {
    private int a;
    private int b;

    public Complex() {

    }

    public int getRealPart() {
        return a;
    }

    public void setRealPart(int a) {
        this.a = a;
    }

    public int getImaginaryPart() {
        return b;
    }

    public void setImaginaryPart(int b) {
        this.b = b;
    }

    public Complex(Complex other) {
        this.a = other.a;
        this.b = other.b;
    }

    public Complex(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public Complex add(Complex c) {
        return new Complex(this.a + c.getRealPart(),this.b + c.getImaginaryPart());
    }

    public Complex minus(Complex C) {
        int aa = this.a - C.a;
        int bb = this.b - C.b;
        return new Complex(aa,bb);
    }

    public Complex multiply(Complex C) {
        int aa = this.a * C.a - this.b * C.b;
        int bb = this.b * C.a + this.a * C.b;
        return new Complex(aa,bb);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complex complex = (Complex) o;
        return a == complex.a && b == complex.b;
    }

    @Override
    public String toString() {
        return a + "" + (b < 0 ? b : ("+" + b)) + "i";
    }
}