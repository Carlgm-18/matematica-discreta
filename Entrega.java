import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;


/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * L'avaluació consistirà en:
 * RandomAccesFile
 * - Si el codi no compila, la nota del grup serà de 0.
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html.  Algunes
 *   consideracions importants: indentació i espaiat consistent, bona nomenclatura de variables,
 *   declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *   declaracions). També convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for
 *   (int i = 0; ...)) sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Carlos Gálvez Mena
 * - Nom 2: Carlos López Mihi
 * - Nom 3: Finn Dicke
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * Els mètodes reben de paràmetre l'univers (representat com un array) i els predicats adients
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és un element de
   * l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true si `P(x)` és
   * cert). Els predicats de dues variables són de tipus `BiPredicate<Integer, Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats retorneu `true`
   * o `false` segons si la proposició donada és certa (suposau que l'univers és suficientment
   * petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * És cert que ∀x ∃!y. P(x) -> Q(x,y) ?
     */
    static boolean exercici1(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {

      for(int x : universe){
        boolean condition = false;
        for(int y : universe){

          if(!(p.test(x) && q.test(x, y))){
            continue;
          }
          if(condition){
            return false;
          }
          condition = true;
        }
        if(!condition){
          return false;
        }
      }
      return true;
    }

    /*
     * És cert que ∃!x ∀y. P(y) -> Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {

      boolean existeUnico = false;

      for(int x : universe){

        boolean cumplePredicado = true;

        for(int y : universe) {
          if(p.test(y) && !q.test(x, y)){
            cumplePredicado = false;
            break;
          }
        }

        if(cumplePredicado){
          if(existeUnico){
            return false;
          }else{
            existeUnico = true;
          }
        }

      }
      return existeUnico;
    }

    /*
     * És cert que ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?
     */
    static boolean exercici3(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
      boolean existeX = false, existeY = false, todoZ = false;
      for(int x : universe){
        for(int y : universe){
          for(int z : universe){
            todoZ = true;
            if(p.test(x, z) == q.test(y, z)/*!((!(p.test(x, z))&&(q.test(y, z))||((p.test(x, z))&&!(q.test(y, z)))))*/){
              todoZ = false;
              break;
            }
          }
          if(todoZ){
            existeY = true;
            break;
          }
        }
        if(existeY){
          existeX = true;
          break;
        }
      }
      if(!todoZ){
        return false;
      }else{
        return existeX;
      }
    }

    /*
     * És cert que (∀x. P(x)) -> (∀x. Q(x)) ?
     */
    static boolean exercici4(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
      boolean todoP = true, todoQ = true;
      for(int x : universe){
        if(!p.test(x)){
          todoP = false;
          break;
        }
      }

      if(!todoP){
        return true;
      }else{
        for(int x : universe){
          if(!q.test(x)){
            todoQ = false;
            break;
          }
        }
        return todoQ;
      }
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // ∀x ∃!y. P(x) -> Q(x,y) ?

      assertThat(
          exercici1(
              new int[] { 2, 3, 5, 6 },
              x -> x != 4,
              (x, y) -> x == y
          )
      );

      assertThat(
          !exercici1(
              new int[] { -2, -1, 0, 1, 2, 3 },
              x -> x != 0,
              (x, y) -> x * y == 1
          )
      );

      // Exercici 2
      // ∃!x ∀y. P(y) -> Q(x,y) ?

      assertThat(
          exercici2(
              new int[] { -1, 1, 2, 3, 4 },
              y -> y <= 0,
              (x, y) -> x == -y
          )
      );

      assertThat(
          !exercici2(
              new int[] { -2, -1, 1, 2, 3, 4 },
              y -> y < 0,
              (x, y) -> x * y == 1
          )
      );

      // Exercici 3
      // ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?

      assertThat(
          exercici3(
              new int[] { 2, 3, 4, 5, 6, 7, 8 },
              (x, z) -> z % x == 0,
              (y, z) -> z % y == 1
          )
      );

      assertThat(
          !exercici3(
              new int[] { 2, 3 },
              (x, z) -> z % x == 1,
              (y, z) -> z % y == 1
          )
      );

      // Exercici 4
      // (∀x. P(x)) -> (∀x. Q(x)) ?

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3, 4, 5, 8, 9, 16 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0 // x és múltiple de 4
          )
      );

      assertThat(
          !exercici4(
              new int[] { 0, 2, 4, 6, 8, 16 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0 // x és múltiple de 4
          )
      );
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits). Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la segona dimensió
   * només té dos elements. Per exemple
   *   int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   *   int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les representam donant el domini
   * int[] a, el codomini int[] b, i f un objecte de tipus Function<Integer, Integer> que podeu
   * avaluar com f.apply(x) (on x és d'a i el resultat f.apply(x) és de b).
   */
  static class Tema2 {
    //Funciones auxiliares
    /**
     * Comprueba que un elemento pertenezca a un conjunto
     * @param a Elemento a comprobar
     * @param rel Conjunto sobre el que se va a comprobar
     * @return Si el elemento está contenido en el conjunto o no
     */
    private static boolean contains(int[] a, int[][] rel){
      boolean cont = false;
      for(int[] y : rel){
        if(Arrays.equals(y, a)){
          cont = true;
          break;
        }
      }
      return cont;
    }

    /**
     * Comprueba que una relación sea reflexiva sobre un conjunto dado
     * @param a Conjunto original
     * @param rel Conjunto relación
     * @return Si la relación es reflexiva o no
     */
    private static boolean reflexiva(int[] a, int[][] rel){
      boolean reflex = true;
      int[] aux = {0, 0};
      for(int x : a){
        aux[0] = x;
        aux[1] = x;
        if(!contains(aux, rel)){
          reflex = false;
          break;
        }
      }
      return reflex;
    }

    /**
     * Comprueba que una relación sea simétrica
     * @param rel Conjunto relacion
     * @return Si la relación es simétrica o no
     */
    private static boolean simetrica(int[][] rel){
      boolean sim = true;
      int[] aux = {0, 0};
      for(int[] x : rel){
        aux[0] = x[1];
        aux[1] = x[0];
        if(!contains(aux, rel)){
          sim = false;
          break;
        }
      }
      return sim;
    }

    /**
     * Comprueba que una relación sea transitiva
     * @param rel Conjunto relación
     * @return Si la relación es transitiva o no
     */
    private static boolean transitiva(int[][] rel){
      boolean trans = true;
      int[] aux = {0, 0};

      for(int[] x : rel){
        for(int[] y : rel){
          if(x[1]==y[0]){
            aux[0] = x[0];
            aux[1] = y[1];
            if(!contains(aux, rel)){
              trans = false;
              break;
            }
          }
        }
      }
      return trans;
    }
    //Fin funciones auxiliares

    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] rel) {
      return reflexiva(a, rel) && simetrica(rel) && transitiva(rel);
    }


    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència. Si ho és, retornau el
     * cardinal del conjunt quocient de `a` sobre `rel`. Si no, retornau -1.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */


    static int exercici2(int[] a, int[][] rel) {
      if(!reflexiva(a, rel) || !simetrica(rel) || !transitiva(rel)){
        return -1;
      }

      Set<Integer>[] claseEquivalencia = new HashSet[a.length]; //Inicializamos un array de Sets

      for (int i = 0; i<claseEquivalencia.length; i++) {
        claseEquivalencia[i] = new HashSet<>();
        claseEquivalencia[i].add(a[i]);
      }
      //Vamos generando las clases de equivalencia para cada elemento de a
      for (int i = 0; i < a.length; i++) {
        for (int[] elem : rel) {
          if(elem[0] == i){
            claseEquivalencia[i].add(elem[1]);
          }

          if(elem[1] == 1){
            claseEquivalencia[i].add(elem[0]);
          }
        }
      }

      Set<Set<Integer>> setEquiv = new HashSet<>(); //Set para eliminar las clases duplicadas
      for(Set<Integer> e: claseEquivalencia){
        setEquiv.add(e);
      }

      return setEquiv.size();

    }

    /*
     * Comprovau si la relació `rel` definida entre `a` i `b` és una funció.
     *
     * Podeu soposar que `a` i `b` estan ordenats de menor a major.
     */

    private static boolean contains(int a, int[] set){
      boolean found = false;
      for (int i : set) {
        if(a == i){
          found = true;
          break;
        }
      }
      return found;
    }
    

    static boolean exercici3(int[] a, int[] b, int[][] rel) {

      for (int[] ints : rel) {
        if(!contains(ints[0], a) || !contains(ints[1], b)){
          return false;
        }
      }
      for (int i : a) {
        int cont = 0;
        for (int[] ints : rel) {
          if(i == ints[0]){
            cont++;
          }
        }
        if(cont != 1){
          return false;
        }
      }
      return true; // TO DO
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`.  Retornau:
     * - Si és exhaustiva, el màxim cardinal de l'antiimatge de cada element de `codom`.
     * - Si no, si és injectiva, el cardinal de l'imatge de `f` menys el cardinal de `codom`.
     * - En qualsevol altre cas, retornau 0.
     *
     * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major.
     */
    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
      boolean inyectiva = true, exhaustiva = true;
      int[] imagen1 = new int[dom.length];
      Set<Integer> imagen2 = new HashSet<>();

      for (int i = 0; i < dom.length; i++) {//Generamos el conjunto de las imágenes
        imagen1[i] = f.apply(dom[i]);
        imagen2.add(imagen1[i]);
      }



      if(codom.length > dom.length) exhaustiva = false;
      for (int a : codom) { //Comprobamos que sea exhaustiva
        if(!imagen2.contains(a)){
          exhaustiva = false;
        }
      }

      for (int i : dom) {//Comprobamos que sea inyectiva
        for (int j : dom) {
          if((f.apply(i).equals(f.apply(j))) && (i != j)){
            inyectiva = false;
          }
        }
      }

      if(exhaustiva){
        int contMax = 0, contI;
        for(int i = 0; i < imagen1.length; i++) {
          contI = 0;
          for(int j = 0; j < imagen1.length; j++) {
            if(imagen1[j] == imagen1[i]){
              contI++;
            }
          }
          if(contI > contMax) contMax = contI;
        }
        return contMax;
      }

      if(inyectiva){
        return imagen2.size() - codom.length;
      }

      return 0;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      // Exercici 1
      // `rel` és d'equivalencia?

      assertThat(
          exercici1(
              new int[] { 0, 1, 2, 3},
              new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 3}, {3, 1} }
          )
      );

      assertThat(
          !exercici1(
              new int[] { 0, 1, 2, 3 },
              new int[][] { {0, 0}, {1, 1}, {2, 2}, {3, 3}, {1, 2}, {1, 3}, {2, 1}, {3, 1} }
          )
      );

      // Exercici 2
      // si `rel` és d'equivalència, quants d'elements té el seu quocient?

      final int[] int09 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

      assertThat(
          exercici2(
            int09,
            generateRel(int09, int09, (x, y) -> x % 3 == y % 3)
          )
          == 3
      );

      assertThat(
          exercici2(
              new int[] { 1, 2, 3 },
              new int[][] { {1, 1}, {2, 2} }
          )
          == -1
      );

      // Exercici 3
      // `rel` és una funció?

      final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(
          exercici3(
            int05,
            int09,
            generateRel(int05, int09, (x, y) -> x == y)
          )
      );

      assertThat(
          !exercici3(
            int05,
            int09,
            generateRel(int05, int09, (x, y) -> x == y/2)
          )
      );

      // Exercici 4
      // el major |f^-1(y)| de cada y de `codom` si f és exhaustiva
      // sino, |im f| - |codom| si és injectiva
      // sino, 0

      assertThat(
          exercici4(
            int09,
            int05,
            x -> x / 4
          )
          == 0
      );

      assertThat(
          exercici4(
            int05,
            int09,
            x -> x + 3
          )
          == int05.length - int09.length
      );

      assertThat(
          exercici4(
            int05,
            int05,
            x -> (x + 3) % 6
          )
          == 1
      );
    }

    /// Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que satisfàn pred.test(a, b)
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      ArrayList<int[]> rel = new ArrayList<>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }

      return rel.toArray(new int[][] {});
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Donarem els grafs en forma de diccionari d'adjacència, és a dir, un graf serà un array
   * on cada element i-èssim serà un array ordenat que contendrà els índexos dels vèrtexos adjacents
   * al i-èssim vèrtex. Per exemple, el graf cicle C_3 vendria donat per
   *
   *  int[][] g = {{1,2}, {0,2}, {0,1}}  (no dirigit: v0 -> {v1, v2}, v1 -> {v0, v2}, v2 -> {v0,v1})
   *  int[][] g = {{1}, {2}, {0}}        (dirigit: v0 -> {v1}, v1 -> {v2}, v2 -> {v0})
   *
   * Podeu suposar que cap dels grafs té llaços.
   */
  static class Tema3 {
    /*
     * Retornau l'ordre menys la mida del graf (no dirigit).
     */
    static int exercici1(int[][] g) {
      int mida = 0;
      for (int[] ints : g) {
        mida += ints.length;
      }

      return g.length - (mida/2);
    }

    /**
     * Comprueba que un grafo sea bipartito utilizando la estrategia de asignar colores a los vértices
     * @param g Grafo a comprobar
     * @param inicio Vértice inicial
     * @param colores Array que signa colores a los vértices
     * @return Si el grafo es bipartido
     */
    static boolean esBipartito(int[][] g, int inicio, int[] colores) {
      int n = g.length;
      colores[inicio] = 1;  // Asignar color 1 al vértice inicial

      int[] cola = new int[n];
      int front = 0;
      int rear = 0;
      cola[rear++] = inicio;

      while (front != rear) {
        int v = cola[front++];

        for (int vecino : g[v]) {
          if (colores[vecino] == 0) {  // Si el vecino no tiene color asignado
            colores[vecino] = -colores[v];  // Asignar el color opuesto al vértice actual
            cola[rear++] = vecino;
          } else if (colores[vecino] == colores[v]) {  // Si el vecino tiene el mismo color que el vértice actual
            return false;  // El grafo no es bipartito
          }
        }
      }

      return true;
    }
    /*
     * Suposau que el graf (no dirigit) és connex. És bipartit?
     */
    static boolean exercici2(int[][] g) {
      int[] colores = new int[g.length];
      Arrays.fill(colores, 0);

      for (int i = 0; i < g.length; i++) {
        if (colores[i] == 0) {
          if (!esBipartito(g, i, colores)) {
            return false;
          }
        }
      }
      return true;
    }

    /**
     * Compruba si un descendiente tiene grado de salida igual a 0 y lo registra en el array correspondiente
     * @param g Grafo DAG
     * @param v Vértice a comprobar
     * @param vistos Array con los vértices ya comprobados
     * @param descendientesGradoSal0 Array con los vértices que tienen grado de salida igual a 0
     */
    static void recorrerArbol(int[][] g, int v, boolean[] vistos, int[] descendientesGradoSal0) {
      vistos[v] = true;

      for (int descendiente : g[v]) {
        if (!vistos[descendiente]) { // Si el descendiente no ha sido visitado...
          recorrerArbol(g, descendiente, vistos, descendientesGradoSal0); //Recursa la función con el nuevo vértice
          descendientesGradoSal0[v] += (g[descendiente].length == 0 ? 1 : descendientesGradoSal0[descendiente]);
        }
      }
    }
    /*
     * Suposau que el graf és un DAG. Retornau el nombre de descendents amb grau de sortida 0 del
     * vèrtex i-èssim.
     */
    static int exercici3(int[][] g, int i) {
      int n = g.length;
      boolean[] vistos = new boolean[n];
      int[] gradoSalida0 = new int[n];

      recorrerArbol(g, i, vistos, gradoSalida0);

      return gradoSalida0[i];
    }


    /*
     * Donat un arbre arrelat (dirigit, suposau que l'arrel es el vèrtex 0), trobau-ne el diàmetre
     * del graf subjacent. Suposau que totes les arestes tenen pes 1.
     */
    static int contarDiametro(int[][] g, int v){
      int dia = 0;
      if(g[v].length == 0){
        return 1;
      }

      for(int i : g[v]){
        dia += contarDiametro(g, i);
      }
      return dia;
    }
    static int exercici4(int[][] g) {
      return contarDiametro(g, 0);
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      final int[][] undirectedK6 = {
        { 1, 2, 3, 4, 5 },
        { 0, 2, 3, 4, 5 },
        { 0, 1, 3, 4, 5 },
        { 0, 1, 2, 4, 5 },
        { 0, 1, 2, 3, 5 },
        { 0, 1, 2, 3, 4 },
      };

      /*
         1
      4  0  2
         3
      */
      final int[][] undirectedW4 = {
        { 1, 2, 3, 4 },
        { 0, 2, 4 },
        { 0, 1, 3 },
        { 0, 2, 4 },
        { 0, 1, 3 },
      };

      // 0, 1, 2 | 3, 4
      final int[][] undirectedK23 = {
        { 3, 4 },
        { 3, 4 },
        { 3, 4 },
        { 0, 1, 2 },
        { 0, 1, 2 },
      };

      /*
             7
             0
           1   2
             3   8
             4
           5   6
      */
      final int[][] directedG1 = {
        { 1, 2 }, // 0
        { 3 },    // 1
        { 3, 8 }, // 2
        { 4 },    // 3
        { 5, 6 }, // 4
        {},       // 5
        {},       // 6
        { 0 },    // 7
        {},
      };


      /*
              0
         1    2     3
            4   5   6
           7 8
      */

      final int[][] directedRTree1 = {
        { 1, 2, 3 }, // 0 = r
        {},          // 1
        { 4, 5 },    // 2
        { 6 },       // 3
        { 7, 8 },    // 4
        {},          // 5
        {},          // 6
        {},          // 7
        {},          // 8
      };

      /*
            0
            1
         2     3
             4   5
                6  7
      */

      final int[][] directedRTree2 = {
        { 1 },
        { 2, 3 },
        {},
        { 4, 5 },
        {},
        { 6, 7 },
        {},
        {},
      };

      assertThat(exercici1(undirectedK6) == 6 - 5*6/2);
      assertThat(exercici1(undirectedW4) == 5 - 2*4);

      assertThat(exercici2(undirectedK23));
      assertThat(!exercici2(undirectedK6));

      assertThat(exercici3(directedG1, 0) == 3);
      assertThat(exercici3(directedRTree1, 2) == 3);

      assertThat(exercici4(directedRTree1) == 5);
      assertThat(exercici4(directedRTree2) == 4);
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 4 (Aritmètica).
   *
   * Per calcular residus podeu utilitzar l'operador %, però anau alerta amb els signes.
   * Podeu suposar que cada vegada que se menciona un mòdul, és major que 1.
   */
  static class Tema4 {
    /*
     * Donau la solució de l'equació
     *
     *   ax ≡ b (mod n),
     *
     * Els paràmetres `a` i `b` poden ser negatius (`b` pot ser zero), però podeu suposar que n > 1.
     *
     * Si la solució és x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */

    /**
     * Calcula el máximo común divisor dados 2 números
     * @param a Primer número
     * @param b Segundo número
     * @return MCD de los argumentos
     */
    static int mcd(int a, int b){
      int r0 = a, r1 = b, t;
      if(b<0) r1*= -1;
      while(r1 > 0){
        t = r1;
        r1 = r0 % r1;
        r0 = t;
      }
      return r0;
    }

    /**
     * Calcula los coeficientes que satisfacen la ecuación ax + by = c
     * @param n Coeficiente de x
     * @param a Coeficiente de y
     * @return Un array que contiene los valores de la solución
     */
    static int[] euclides(int n, int a){
      boolean negA = false;
      int r0 = n, r1 = a, t;
      int x0 = 1, y0 = 0, q;
      int x1 = 0, y1 = 1, auxX, auxY;

      if(a < 0){
        negA = true;
        r1 *= -1;
      }

      while(r1 > 0){
        t = r1;
        r1 = r0 % r1;
        if(r1 <= 0){
          break;
        }
        q = r0/t;
        r0 = t;

        auxX = x1;
        auxY = y1;

        x1 = x0 - q*x1;
        y1 = y0 - q*y1;

        x0 = auxX;
        y0 = auxY;
      }

      if(negA) y1 *= -1;

      return new int[]{x1, y1};
    }
    static int[] exercici1(int a, int b, int n) {
      int d = mcd(n, a);

      if((b%d) != 0){
        return null;
      }

      int[] sol = euclides(n, a);
      int c = sol[1];
      int k = n/d;

      if(k < 0) k *= -1;

      while(c < 0){
        c += k;
      }

      return new int[]{c, k};
    }

    /*
     * Donau la solució (totes) del sistema d'equacions
     *
     *  { x ≡ b[0] (mod n[0])
     *  { x ≡ b[1] (mod n[1])
     *  { x ≡ b[2] (mod n[2])
     *  { ...
     *
     * Cada b[i] pot ser negatiu o zero, però podeu suposar que n[i] > 1. També podeu suposar
     * que els dos arrays tenen la mateixa longitud.
     *
     * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici2a(int[] b, int[] n) {
      int[] P = new int[n.length];
      Arrays.fill(P, 1);
      int[] Q = new int[n.length];
      int c = 0, m = 1;

      for (int i = 0; i < n.length; i++) {
        for (int j = 0; j < n.length; j++) {
          if(i == j) continue;
          if(mcd(n[i], n[j]) != 1) return null;

          P[i] *= n[j];

        }
        Q[i] = euclides(n[i], P[i]%n[i])[1];
        c += P[i]*Q[i]*b[i];
        m *= n[i];
      }

      while(c < 0){
        c += m;
      }
      return new int[]{c, m};
    }

    /*
     * Donau la solució (totes) del sistema d'equacions
     *
     *  { a[0]·x ≡ b[0] (mod n[0])
     *  { a[1]·x ≡ b[1] (mod n[1])
     *  { a[2]·x ≡ b[2] (mod n[2])
     *  { ...
     *
     * Cada a[i] o b[i] pot ser negatiu (b[i] pot ser zero), però podeu suposar que n[i] > 1. També
     * podeu suposar que els tres arrays tenen la mateixa longitud.
     *
     * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici2b(int[] a, int[] b, int[] n) {

      int[] b1 = new int[b.length];
      int[] n1 = new int[n.length];
      int[] sol;

      for(int i = 0; i < a.length; i++){
        sol = exercici1(a[i], b[i], n[i]);
        if(sol == null) return null;
        b1[i] = sol[0];
        n1[i] = sol[1];
      }
      return exercici2a(b1, n1);
    }

    /*
     * Suposau que n > 1. Donau-ne la seva descomposició en nombres primers, ordenada de menor a
     * major, on cada primer apareix tantes vegades com el seu ordre. Per exemple,
     *
     * exercici4a(300) --> new int[] { 2, 2, 3, 5, 5 }
     *
     * No fa falta que cerqueu algorismes avançats de factorització, podeu utilitzar la força bruta
     * (el que coneixeu com el mètode manual d'anar provant).
     */

    static ArrayList<Integer> factorizar(int n){
      ArrayList<Integer> factores = new ArrayList<>();
      int num = n;

      for(int i = 2; i <= n; i++){
        while(num%i == 0){
          num /= i;
          factores.add(i);
        }
      }
      return factores;
    }
    static ArrayList<Integer> exercici3a(int n) {
      return factorizar(n);
    }

    /*
     * Retornau el nombre d'elements invertibles a Z mòdul n³.
     *
     * Alerta: podeu suposar que el resultat hi cap a un int (32 bits a Java), però n³ no té perquè.
     * De fet, no doneu per suposat que pogueu tractar res més gran que el resultat.
     *
     * No podeu utilitzar `long` per solucionar aquest problema. Necessitareu l'exercici 3a.
     */

      static int pow(int a, int x){
        int aux = 1;

        for (int i = 0; i < x; i++) {
          aux *= a;
        }

        return aux;
      }

    static int exercici3b(int n) {

      int invertibles = 1;
      ArrayList<Integer> factores = factorizar(n);
      List<Integer> unicos = new ArrayList<>(new HashSet<>(factores));
      int[] cont = new int[unicos.size()];
      Arrays.fill(cont, 0);

      for(Integer factor : factores){
        cont[unicos.indexOf(factor)] += 1;
      }

      for (int i = 0; i < cont.length; i++) {
        cont[i] *= 3;
      }

      for (int i = 0; i<unicos.size(); i++) {
          invertibles *= pow(unicos.get(i), cont[i]) - pow(unicos.get(i), cont[i] - 1);
      }

      return invertibles;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu `main`)
     */
    static void tests() {
      assertThat(Arrays.equals(exercici1(17, 1, 30), new int[] { 23, 30 }));
      assertThat(Arrays.equals(exercici1(-2, -4, 6), new int[] { 2, 3 }));
      assertThat(exercici1(2, 3, 6) == null);

      assertThat(
        exercici2a(
          new int[] { 1, 0 },
          new int[] { 2, 4 }
        )
        == null
      );

      assertThat(
        Arrays.equals(
          exercici2a(
            new int[] { 3, -1, 2 },
            new int[] { 5,  8, 9 }
          ),
          new int[] { 263, 360 }
        )
      );

      assertThat(
        exercici2b(
          new int[] { 1, 1 },
          new int[] { 1, 0 },
          new int[] { 2, 4 }
        )
        == null
      );

      assertThat(
        Arrays.equals(
          exercici2b(
            new int[] { 2,  -1, 5 },
            new int[] { 6,   1, 1 },
            new int[] { 10,  8, 9 }
          ),
          new int[] { 263, 360 }
        )
      );

      assertThat(exercici3a(10).equals(List.of(2, 5)));
      assertThat(exercici3a(1291).equals(List.of(1291)));
      assertThat(exercici3a(1292).equals(List.of(2, 2, 17, 19 )));

      assertThat(exercici3b(10) == 400);

      // Aquí 1292³ ocupa més de 32 bits amb el signe, però es pot resoldre sense calcular n³.
      assertThat(exercici3b(1292) == 961_496_064);

      // Aquest exemple té el resultat fora de rang
      //assertThat(exercici3b(1291) == 2_150_018_490);
    }
  }

  /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor sigui `true`.
   */
  public static void main(String[] args) {
    //Tema1.tests();
    //Tema2.tests();
    //Tema3.tests();
    Tema4.tests();

  }

  /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}
