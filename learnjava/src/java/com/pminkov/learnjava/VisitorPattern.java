package com.pminkov.learnjava;

interface Expression {
  void accept(Visitor v);
}

class Operator implements Expression {
  public Expression left, right;
  private String op;

  Operator(String op, Expression left, Expression right) {
    this.op = op;
    this.left = left;
    this.right = right;
  }

  @Override
  public void accept(Visitor v) {
    v.visit(this);
  }

  @Override
  public String toString() {
    return "Operator(" + op + ")";
  }
}

class Value implements Expression {
  private int value;

  Value(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "Value(" + value + ")";
  }

  @Override
  public void accept(Visitor v) {
    v.visit(this);
  }
}


interface Visitor {
  void visit(Operator op);
  void visit(Value v);
}

class PrintingVisitor implements Visitor {
  int depth = 0;

  void writeSpaces() {
    for (int i = 0; i < 10-depth; i++) {
      System.out.print(' ');
    }
  }

  public void visit(Operator op) {
    depth += 2;
    op.left.accept(this);
    depth -= 2;
    writeSpaces();

    depth += 2;
    System.out.println(op.toString());
    op.right.accept(this);
    depth -= 2;
  }

  public void visit(Value v) {
    writeSpaces();
    System.out.println(v.toString());
  }
}

class DepthCalculatingVisitor implements Visitor {
  int depth = 0;
  int maxDepth = 0;

  public void visit(Operator op) {
    depth += 1;
    if (depth > maxDepth) {
      maxDepth = depth;
    }
    op.left.accept(this);
    op.right.accept(this);
    depth -= 1;
  }

  public void visit(Value v) {
  }
}

class LameDepthCalculator {
  public int calcDepth(Expression ex) {
    if (ex instanceof Operator) {
      return implCalcDepth((Operator) ex);
    } else if (ex instanceof Value) {
      return implCalcDepth((Value) ex);
    } else {
      return 0;
    }
  }

  private int implCalcDepth(Operator op) {
    return 1+Math.max(
        calcDepth(op.left),
        calcDepth(op.right)
    );
  }

  private int implCalcDepth(Value val) {
    return 0;
  }
}

public class VisitorPattern {
  public static Expression buildExpression() {
    // Expr = (3+4) * 16

    Value v3 = new Value(3);
    Value v4 = new Value(4);
    Value v16 = new Value(16);

    Operator sumOp = new Operator("+", v3, v4);

    Operator multOp = new Operator("*", sumOp, v16);

    return multOp;
  }
  public static void main(String[] args) {
    Expression expr = buildExpression();

    PrintingVisitor pv = new PrintingVisitor();
    expr.accept(pv);

    DepthCalculatingVisitor dcv = new DepthCalculatingVisitor();
    expr.accept(dcv);
    System.out.println("Depth=" + dcv.maxDepth);

    LameDepthCalculator ldc = new LameDepthCalculator();
    System.out.println(ldc.calcDepth(expr));
  }
}
