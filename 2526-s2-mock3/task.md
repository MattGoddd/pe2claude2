# CS2030S AY25/26 Sem 2
## Practical Assessment 2 — Mock 3

## INSTRUCTIONS TO CANDIDATES

1. The total mark is 40.  We may deduct up to 4 marks for violation of
   style.

2. This is a CLOSED-BOOK assessment.  You are only allowed to refer to one
   double-sided A4-size paper.

3. You should see the following files/directories in your home directory.

   * `Order.java` and `Shop.java` — for you to edit and solve Question 1.

   * `cs2030s/fp/` — contains the full implementations of `Maybe<T>` and
     `Lazy<T>`, along with the functional interfaces `Transformer<T,R>`,
     `Producer<T>`, and `BooleanCondition<T>`.  Do NOT edit any file in
     this directory.

   * `test.jar` — contains automated tests `Test1`, `Test2`, and `Test3`
     for Question 1, and `Test4` through `Test7` for Question 2.

   * `ListAPI.md` and `StreamAPI.md` — API references.

   * The subdirectory `Q2/`, which contains:

     * `Record.java` — provided, do NOT edit.

     * `Q2a.java` to `Q2d.java` — working imperative code for you to
       refactor.

     * `Demo.java` — demonstrates expected output for each method.

4. Write your student number on top of EVERY FILE you created or edited
   as part of the `@author` tag.  Do not write your name.

5. To compile your code for Question 1, run from your home directory:

   ```
   javac -Xlint:unchecked -Xlint:rawtypes -cp . cs2030s/fp/*.java *.java
   ```

   For Question 2, run from inside the `Q2/` subdirectory:

   ```
   javac -Xlint:unchecked -Xlint:rawtypes -cp .. cs2030s/fp/*.java *.java
   ```

---

## Question 1 (24 marks): Applying Lazy and Maybe

You are given two classes, `Order` and `Shop`, which form the backbone of
a simple shop management system.  Both classes are currently written in an
imperative style: `Order` is mutable and uses `null` to represent an
absent coupon code; `Shop` contains loops, null checks, and conditional
statements.

Your task is to rewrite them using the `Maybe<T>` and `Lazy<T>` classes
provided in the `cs2030s.fp` package.  Read through both files carefully
before you start — the Javadoc comments describe exactly what each method
must do and what you must change.

### The `Order` class

Each order records an ID, a customer ID, a base `amount`, whether it has
been `paid`, and an optional coupon (a code string and a discount
percentage).

### The `Shop` class

A shop maintains a `Map<String, Order>` of orders keyed by order ID.
It provides methods to look up, pay, and query orders.

---

### Task 1 (8 marks): Make `Order` Immutable and Use `Maybe`

Rewrite `Order.java` so that:

1. All fields are `private final`.

2. The two fields `String coupon` and `double discountPct` are replaced
   by `Maybe<String> coupon` and `Maybe<Double> discountPct`.

3. Every method that previously mutated the object now returns a **new**
   `Order` instead.  The method signatures (names, parameter types, and
   return types) must not be changed — except that `getCoupon()` now
   returns `Maybe<String>` and `getDiscountPct()` returns `Maybe<Double>`.

4. `getFinalAmount()` computes the payable amount using `Maybe.map` and
   `Maybe.orElse` instead of null checks — no `if`, no `? :`.

You are not allowed to add new public methods.

After completing Task 1:

```
jshell> Order o = new Order("O1", "Alice", 100.0)
o ==> Order(O1, Alice, $100.00, unpaid, coupon=None)

jshell> o.getCoupon()
$2 ==> None

jshell> o.getFinalAmount()
$3 ==> 100.0

jshell> Order paid = o.pay()
paid ==> Order(O1, Alice, $100.00, paid, coupon=None)

jshell> o.isPaid()    // original unchanged
$5 ==> false

jshell> Order coupon = o.applyCoupon("SAVE10", 10.0)
coupon ==> Order(O1, Alice, $100.00, unpaid, coupon=Some(SAVE10))

jshell> coupon.getCoupon()
$7 ==> Some(SAVE10)

jshell> coupon.getDiscountPct()
$8 ==> Some(10.0)

jshell> coupon.getFinalAmount()
$9 ==> 90.0

jshell> o.getCoupon()    // original still unchanged
$10 ==> None
```

When you are ready, run the automated test:

```
$ java -cp test.jar:. Test1
```

---

### Task 2 (8 marks): Rewrite `Shop` Using `Maybe` and Streams

Rewrite the following methods in `Shop.java` so that each method body
consists of a **single `return` statement** and contains:

- **No loops** (`for`, `while`, `do-while`)
- **No conditional statements** (`if`, `switch`, `? :`)
- **No null literals**
- **No block lambdas** (`x -> { ... }`)

You must also change the return type of `findOrder` from `Order` to
`Maybe<Order>`.  The remaining method signatures stay the same.

The methods to rewrite are:

- `findOrder(String id)` — return `Maybe.none()` if not found, otherwise
  `Maybe.some(order)`.

- `getCoupon(String id)` — chain through `findOrder` using `flatMap` to
  reach the order's `Maybe<String>` coupon.

- `getDiscountPct(String id)` — two-level `flatMap`: first through the
  order, then through the coupon.

- `getFinalAmount(String id)` — map over `findOrder` to extract the final
  payable amount.

- `payOrder(String id)` — if the order exists, replace the entry in the
  map with the paid order.  Does nothing if not found.  Reset the
  `cachedRevenue` Lazy afterwards (see Task 3).

- `totalRevenue()` — return `cachedRevenue.get()` (see Task 3).

- `unpaidOrderIds()` — return a list of order IDs for all unpaid orders.

- `ordersWithCoupon()` — return a sorted list of order IDs where
  `getCoupon()` is not `None`.

**Important:** `findOrder` must use `Maybe` operations only — no null
checks, no `if`.  The idiomatic pattern is:

```java
return Maybe.some(orders.get(id)).filter(o -> !o.equals(null));
// Or equivalently, using any condition that selects the non-null case.
```

When you are ready, run the automated test:

```
$ java -cp test.jar:. Test2
```

---

### Task 3 (8 marks): Cache `totalRevenue()` with `Lazy`

Computing the total revenue by summing over all paid orders is expensive.
We want to cache the result so it is only recomputed when the order map
actually changes.

Extend `Shop.java` as follows:

1. Add a **private** field `cachedRevenue` of type `Lazy<Double>`.

2. Initialise `cachedRevenue` in the constructor as a deferred `Lazy` —
   it should not compute the revenue until `get()` is first called.

3. Rewrite `totalRevenue()` to return `cachedRevenue.get()`.

4. In **both** `addOrder` and `payOrder`, reset `cachedRevenue` to a new
   deferred `Lazy` so that the next call to `totalRevenue()` recomputes
   the revenue from the updated map.

After completing Task 3, the revenue computation is memoised between
changes:

```
jshell> Shop shop = new Shop()

jshell> shop.totalRevenue()
$2 ==> 0.0

jshell> shop.addOrder(new Order("O1","A",100.0).pay())

jshell> shop.totalRevenue()    // now recomputed: 100.0
$4 ==> 100.0

jshell> shop.totalRevenue()    // cached — same result, not recomputed
$5 ==> 100.0

jshell> shop.addOrder(new Order("O2","B",200.0).pay())

jshell> shop.totalRevenue()    // recomputed after addOrder: 300.0
$7 ==> 300.0

jshell> shop.payOrder("O3")    // non-existent — no effect on revenue

jshell> shop.totalRevenue()    // still 300.0
$9 ==> 300.0
```

When you are ready, run the automated test:

```
$ java -cp test.jar:. Test3
```

---

## Question 2 (16 marks): Streams with `Maybe`

Under the subdirectory `Q2`, you are provided:

- `Record.java` — a class representing a student's module record, with a
  student ID, module code, optional numeric grade (`Maybe<Double>`),
  optional tutor name (`Maybe<String>`), and semester number.

- `Q2a.java` to `Q2d.java` — working imperative implementations to
  refactor.

Your task is to refactor each method into a **single Stream pipeline**,
returned as a **single `return` statement**.

**IMPORTANT:** Each method body must:

- Consist of exactly **one statement** — a `return` statement.
- Contain **no loop** and **no conditional statement** (`if`, `switch`, `? :`).
- **Not** use block lambdas (`x -> { ... }`).

The `Maybe` API is available in `cs2030s.fp.Maybe`.  Useful patterns:

- To keep only records where a `Maybe` field is present:
  `.filter(r -> !r.getMaybeField().equals(Maybe.none()))`
- To extract a value from a `Maybe` or supply a default:
  `.map(r -> r.getMaybeField().orElse("default"))`
- To flatten a `Maybe<T>` inside a stream into individual elements or
  nothing:
  `.flatMap(r -> r.getMaybeField().map(Stream::of).orElse(Stream.empty()))`

Compile from inside `Q2/`:

```
javac -Xlint:unchecked -Xlint:rawtypes -cp .. cs2030s/fp/*.java *.java
java Demo
java -cp ../test.jar:. Test4
java -cp ../test.jar:. Test5
java -cp ../test.jar:. Test6
java -cp ../test.jar:. Test7
```

### Part (a) (3 marks): getCompletedGrades

This method takes a list of records and returns a **sorted** (ascending)
`List<Double>` of all numeric grades from records where the grade is
present (i.e. not `None`).  Records with a `None` grade are excluded.

Write your class so that it behaves as follows:

```
jshell> /open Record.java
jshell> /open Q2a.java

jshell> List<Record> records = List.of(
   ...>   new Record("A001","CS2030S", Maybe.some(85.0), Maybe.some("Prof Alice"), 1),
   ...>   new Record("A002","CS2030S", Maybe.some(72.0), Maybe.some("Prof Alice"), 1),
   ...>   new Record("A003","CS2030S", Maybe.none(),     Maybe.some("Prof Alice"), 1),
   ...>   new Record("A004","CS2101",  Maybe.some(90.0), Maybe.some("Prof Bob"),   1),
   ...>   new Record("A005","CS2101",  Maybe.some(65.0), Maybe.none(),             1),
   ...>   new Record("A006","CS2101",  Maybe.none(),     Maybe.some("Prof Bob"),   2),
   ...>   new Record("A007","CS2030S", Maybe.some(78.0), Maybe.some("Prof Carol"), 2),
   ...>   new Record("A008","CS2030S", Maybe.some(91.0), Maybe.some("Prof Carol"), 2));

jshell> Q2a.getCompletedGrades(records)
$.. ==> [65.0, 72.0, 78.0, 85.0, 90.0, 91.0]
```

### Part (b) (3 marks): getGradeSummaries

This method takes a list of records and returns a `List<String>` in input
order, where each string is:

- `"<studentId>: <grade>"` (grade formatted to 1 decimal place) if the
  grade is present, or
- `"<studentId>: incomplete"` if the grade is `None`.

You **must** use `Maybe.map` and `Maybe.orElse` to construct each string
— no `if` or `? :`.

Write your class so that it behaves as follows (continuing from above):

```
jshell> /open Q2b.java

jshell> Q2b.getGradeSummaries(records)
$.. ==> [A001: 85.0, A002: 72.0, A003: incomplete, A004: 90.0,
         A005: 65.0, A006: incomplete, A007: 78.0, A008: 91.0]
```

### Part (c) (5 marks): getModuleTutors

This method takes a list of records and a module code.  It returns a
**sorted, distinct** `List<String>` of tutor names for records belonging
to that module.  Records with no tutor (`None`) are excluded.

This question requires flattening a `Maybe<String>` tutor field into
the stream — use the pattern:
`.flatMap(r -> r.getTutor().map(Stream::of).orElse(Stream.empty()))`

Write your class so that it behaves as follows (continuing from above):

```
jshell> /open Q2c.java

jshell> Q2c.getModuleTutors(records, "CS2030S")
$.. ==> [Prof Alice, Prof Carol]

jshell> Q2c.getModuleTutors(records, "CS2101")
$.. ==> [Prof Bob]
```

### Part (d) (5 marks): getSharedTutorPairs

This method takes a list of records and returns a `List<List<String>>`
containing all distinct ordered pairs of student IDs that share the
**same** tutor (where that tutor is `Some`, not `None`).  A pair
`[a, b]` appears only once, with `a` appearing earlier in the input list
than `b`.

This is the hardest question.  It is equivalent to a double `for`-loop
but must be written as a nested `flatMap`.  Key points:

- Use `records.indexOf(r2) > records.indexOf(r1)` to enforce `i < j`.
- Filter out records with `None` tutor before comparing.
- Use `Maybe.equals` to check if two tutor fields are equal:
  `r1.getTutor().equals(r2.getTutor())`.

Write your class so that it behaves as follows (continuing from above):

```
jshell> /open Q2d.java

jshell> Q2d.getSharedTutorPairs(records)
$.. ==> [[A001, A002], [A001, A003], [A002, A003],
         [A004, A006], [A007, A008]]
```

Note: `A005` has no tutor (`None`), so it does not appear in any pair.
Two records both having `None` tutor do **not** form a pair.

## END OF PAPER
