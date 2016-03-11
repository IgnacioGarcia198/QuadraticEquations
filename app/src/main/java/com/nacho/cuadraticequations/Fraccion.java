package com.nacho.cuadraticequations;

/**
 * Created by nacho on 03/05/2015.
 */
public class Fraccion { // fraccion de la forma a/b
    int a, b;
    public Fraccion(int a, int b) {
        this.a = a;
        this.b = b;
    }

    Fraccion suma(Fraccion f1, Fraccion f2) {
        if(f1.b == f2.b) {
            return new Fraccion(f1.a+f2.a,f1.b);
        }
        else {
            int bb = mcm(f1.b, f2.b);
            return new Fraccion(f1.a*bb/f1.b + f2.a*bb/f2.b, bb);
        }
    }

    Fraccion resta(Fraccion f1, Fraccion f2) {
        if(f1.b == f2.b) {
            return new Fraccion(f1.a-f2.a,f1.b);
        }
        else {
            int bb = mcm(f1.b, f2.b);
            return new Fraccion(f1.a*bb/f1.b - f2.a*bb/f2.b, bb);
        }
    }

    Fraccion producto(Fraccion f1, Fraccion f2) {
        return new Fraccion(f1.a*f2.a, f1.b*f2.b);
    }

    Fraccion division(Fraccion f1, Fraccion f2) {
        return new Fraccion(f1.a*f2.b, f1.b*f2.a);
    }

    void simplifica() {
        int div = mcd(a, b);
        a /= div;
        b /= div;
    }

    int mcd(int x, int y) {
        if(x >= y) {
            if(x % y == 0) {
                return y;
            }
            for(int i = y; i > 1; i --) {
                if((y%i == 0) && (x%i == 0)) {
                    return i;
                }
            }
        }
        else {
            if(y % x == 0) {
                return x;
            }
            for(int i = x; i > 1; i --) {
                if((y%i == 0) && (x%i == 0)) {
                    return i;
                }
            }
        }
        return 1;
    }

    int mcm(int x, int y)  {
        if(x <= y) {
            if(y%x == 0) {
                return y;
            }
            for(int i = x; i <= y; i ++) {
                int multiplo = x*i;
                if(multiplo % y == 0) {
                    return multiplo;
                }
            }
        }
        else {
            if(x%y == 0) {
                return x;
            }
            for(int i = y; i <= x; i ++) {
                int multiplo = y*i;
                if(multiplo % x == 0) {
                    return multiplo;
                }
            }
        }
        return x*y;
    }

    public String toString() {
        if(Math.abs(b) == 1) {
            return "" + (a/b);
        }
        return "" + a + "/" + b;
    }
}
