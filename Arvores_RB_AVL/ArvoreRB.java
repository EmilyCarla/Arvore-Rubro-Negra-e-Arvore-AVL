import java.util.Random;

public class ArvoreRB<T extends Comparable<T>> {
    private Node<T> root;
    private Node<T> nil;

    // Definição da classe Node
    private static class Node<T extends Comparable<T>> {
        T key;
        Node<T> parent;
        Node<T> left;
        Node<T> right;
        int color; // 0 for black, 1 for red

        Node(T key, int color, Node<T> nil) {
            this.key = key;
            this.color = color;
            this.parent = nil;
            this.left = nil;
            this.right = nil;
        }
    }

    public ArvoreRB() {
        nil = new Node<>(null, 0, null);
        root = nil;
    }

    public void insert(T key) {
        Node<T> newNode = new Node<>(key, 1, nil);
        insert(newNode);
        fixInsert(newNode);
    }

    private void insert(Node<T> newNode) {
        Node<T> parent = null;
        Node<T> current = root;

        while (current != nil) {
            parent = current;
            if (newNode.key.compareTo(current.key) < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        newNode.parent = parent;
        if (parent == null) {
            root = newNode;
        } else if (newNode.key.compareTo(parent.key) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        newNode.left = nil;
        newNode.right = nil;
        newNode.color = 1; // Red
        fixInsert(newNode);
    }

    private void fixInsert(Node<T> node) {
        while (node.parent != null && node.parent.color == 1) {
            if (node.parent == node.parent.parent.left) {
                Node<T> uncle = node.parent.parent.right;
                if (uncle.color == 1) {
                    node.parent.color = 0;
                    uncle.color = 0;
                    node.parent.parent.color = 1;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = 0;
                    node.parent.parent.color = 1;
                    rightRotate(node.parent.parent);
                }
            } else {
                Node<T> uncle = node.parent.parent.left;
                if (uncle.color == 1) {
                    node.parent.color = 0;
                    uncle.color = 0;
                    node.parent.parent.color = 1;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = 0;
                    node.parent.parent.color = 1;
                    leftRotate(node.parent.parent);
                }
            }
        }
        root.color = 0;
    }

    private void leftRotate(Node<T> x) {
        Node<T> y = x.right;
        x.right = y.left;
        if (y.left != nil) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node<T> y) {
        Node<T> x = y.left;
        y.left = x.right;
        if (x.right != nil) {
            x.right.parent = y;
        }
        x.parent = y.parent;
        if (y.parent == null) {
            root = x;
        } else if (y == y.parent.left) {
            y.parent.left = x;
        } else {
            y.parent.right = x;
        }
        x.right = y;
        y.parent = x;
    }

    public void insertAll(T[] keys) {
        for (T key : keys) {
            insert(key);
        }
    }

    public long calculateExecutionTime(T[] keys) {
        long startTime = System.nanoTime();
        insertAll(keys);
        long endTime = System.nanoTime();

        return endTime - startTime;
    }

    public void inOrderTraversal() {
        System.out.print("Dados organizados: ");
        inOrderTraversal(root);
        System.out.println();
    }

    private void inOrderTraversal(Node<T> node) {
        if (node != nil) {
            inOrderTraversal(node.left);
            System.out.print(node.key + " ");
            inOrderTraversal(node.right);
        }
    }

    public void performRandomOperations() {
        Random random = new Random();

        for (int i = 0; i < 50000; i++) {
            int randomNumber = random.nextInt(19999) - 9999; // Números entre -9999 e 9999

            if (randomNumber % 3 == 0) {
                // Número é múltiplo de 3, inserir na árvore
                insert((T) Integer.valueOf(randomNumber));
            } else if (randomNumber % 5 == 0) {
                // Número é múltiplo de 5, remover da árvore
                remove((T) Integer.valueOf(randomNumber));
            } else {
                // Número não é múltiplo de 3 nem de 5, contar ocorrências
                int occurrences = countOccurrences((T) Integer.valueOf(randomNumber));
                System.out.println("Número " + randomNumber + " aparece na árvore " + occurrences + " vezes.");
            }
        }
    }

    private int countOccurrences(T key) {
        return countOccurrences(root, key);
    }

    private int countOccurrences(Node<T> node, T key) {
        if (node == nil) {
            return 0;
        }

        int compareResult = key.compareTo(node.key);
        if (compareResult < 0) {
            return countOccurrences(node.left, key);
        } else if (compareResult > 0) {
            return countOccurrences(node.right, key);
        } else {
            return 1 + countOccurrences(node.left, key) + countOccurrences(node.right, key);
        }
    }

    private void remove(T key) {
        Node<T> node = search(root, key);
        if (node != nil) {
            delete(node);
        }
    }

    private void delete(Node<T> z) {
        // Implementação da remoção deve ser feita aqui
    }

    private Node<T> search(Node<T> x, T key) {
        while (x != nil && !key.equals(x.key)) {
            if (key.compareTo(x.key) < 0) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        return x;
    }

    public static void main(String[] args) {
        ArvoreRB<Integer> rbTree = new ArvoreRB<>();

        
        Integer[] data = {-935, 12562, -2467, -8197, 1123, 1963, -1596, 4867, -1016, 3510, -6282, 5077, -6252, -9250, -5893, 11274, -8077, 9950, -2882, -1850, 4433, -2708, -328, -5106, -5214, -8374, 5439, 12351, 4323, 1699, 15798, -8356, 15395, 8778, -1284, 14592, -1457, 14936, -8862, -6625, -8681, -142, 666, 220, -6528, 10406, -15480, 9645, 573, -1358, 4591, -785, 9659, -3913, 3685, -2159, -1188, -12842, -10948, -5305, 1413, 8384, 5835, 1121, -13519, -3074, 9092, -2473, 15852, 4448, -4689, -393, -1031, -36, 9049, -9405, -10969, -11083, -5974, 7626, -6598, -3797, -7347, 2791, -5354, -1200, 2242, 9351, 817, -9696, -8347, -281, 539, -1252, -542, 5312, -3695, -10552, -6770, 8063, -4726, 10586, -1340, 2707, -7998, -13684, 7648, 8346, -275, -9400, 4279, 5899, -17091, -1478, 758, -8394, 352, -10328, 4462, 8235, -10608, 4668, 8464, 42, 11111, 8947, 498, 2302, -7280, -5925, -9706, 1915, 3680, 12865, -3608, 4318, 12494, -1768, 3076, -1213, -7222, -411, 13728, -3213, -16173, 2641, 8510, -3327, -14370, -498, -15206, -1740, -10767, -2902, 6680, -6616, 1778, 253, 4145, -4395, -14607, -8514, -3042, -10529, -5514, 14376, -11371, -1564, -1893, 8745, 4487, -12658, 3731, 512, -2289, 18049, 4230, 3817, -14124, -2697, -6356, -10761, 300, -8642, 7432, 4297, -13106, 5371, -13566, -1208, 9759, 13437, -9710, -11077, -4130, 5028, -46, 3739, -13518, -17898, -4893, 12523, -2641, -2433, 8021, -3402, -3595, -18519, -2070, 3231, -1582, -4770, 2847, 2691, -2346, 1501, -6020, -715, 14542, 8503, -5206, -12369, -8899, 2408, -3270, -8012, -9591, 1588, -11432, -9713, 6545, 11744, -6268, 1267, 5497, -1294, -2777, 14561, 7306, -3701, -448, 4884, -1186, 11234, -7853, -6622, 8592, 2098, 999, -5936, -6025, -1172, -9018, -2441, 4895, -783, -9040, 885, 9680, -16030, 2583, -13370, -4572, 12081, -7528, 5066, 9445, -8927, 12099, 1532, 9598, 12115, -5541, 623, -1468, 416, 7859, 7634, -5151, 5459, -3831, 3124, -2847, 3309, -3039, 235, -12342, 7274, -3522, 14645, 6528, -1938, -7260, -16881, -15548, 6652, 1320, 3743, 1994, -11159, -3544, -3533, -9394, 1409, 5644, 11250, 7589, -17466, 903, 4526, -1936, 6021, 9845, 14182, 1900, -6399, 3450, -13322, 154, 8100, -443, -14562, 15568, -8919, -1567, -15192, -5970, 3505, -14834, 2668, -7812, -13043, -637, -8, 15551, -15020, -4602, 12629, -10168, -383, -8225, -7782, 6259, 4719, 3541, -5364, 519, -6429, 10544, -312, 9639, -6519, -3875, -4702, -7208, -6156, 1486, 7913, -3592, 1139, -11433, -12165, 7004, -7917, 16875, 11787, -2001, -8336, 5501, -14408, -352, -9605, 1090, 9068, -16633, -1240, -2324, 12369, -17196, -10460, 710, 6860, -17049, 649, 782, -2796, -232, 4370, 4918, -500, 13497, 6704, 279, 6074, -307, 14316, -2240, -5257, -3222, -2722, -2181, -6914, 3132, -6953, -2682, 15847, -988, 3785, 6709, -7880, 2667, 11341, 8522, -9984, -9183, -10965, -9976, -153, 5037, -3801, -4355, 436, -10835, -4354, 1131, -11284, 12749, -7751, 1404, -10604, 2744, -12331, 5805, 959, -2217, 1806, -322, 2602, -3075, -1874, 7794, -9836, -6100, -13830, -998, -8678, 1511, -5111, -10163, -1525, 4160, 11576, -10135, -2063, 2226, 5456, 8065, -9169, 9268, -3048, 9723, 1383, -8507, -827, -7300, -15853, -2065, -8452, 5279, -6428, 664, 1385, 6835, 13433, -15398, -6975, 3417, -1322, 1942, -2447, 9060, -8050, -1100, 12372, -1891, 6375, 8920, 3705, 6869, 3984, 6356, -4192, 1405, 12148, 6366, -5898, 2807, -5070, 1158, 465, -11586, 3735, 7067, -10258, 5305, 2908, -7478, -4529, 9201, -7909, -16636, -520, -3278, 2546, 6795, -9283, -5170, -1836, -347, 11159, -7236, -6556, -13504, -13375, -1497, 5086, 1506, 391, 8909, -11703, -75, -3430, -13047, -2535, 2938, 5513, -8342, -6624, 4083, 7616, 5582, -1410, 2242, -4854, 1919, -5211, -8632, -7049, 4114, -2225, -1615, 6963, -5577, -11721, -11899, -5432, 222, -5611, -13305, 8041, -12848, -3760, -10033, 6329, -7514, -1551, 1161, 15819, 2766, 11611, -465, -2120, -1009, -10714, 10423, -31, -7808, 7486, -1180, 8783, 13064, 6397, 8985, -5609, 2637, 4657, 9771, 6286, -496, 1568, 3387, 5954, 1599, -6715, 6819, -14649, 3029, -3306, 908, 4840, -8363, 3299, 5691, -15652, -1820, 16392, 4395, -125, -8459, -7662, 6500, -5819, -1559, 406, 6198, 2762, -4929, 4136, 6176, -4683, -4945, 9300, -2553, 557, -1194, -9787, -8709, -1526, -2789, -8634, -2546, 3254, 3950, -5313, -8263, -5460, -9673, -7963, 3465, 3061, 16392, 4189, 14924, -1795, 886, 16186, -12651, -1228, 5707, -7357, -1443, 6612, -587, 1762, 4834, -918, 6296, -9918, -3608, 16528, 2825, -3457, -4257, 6278, 15787, -9724, -4885, -3196, -14992, 9155, 680, -6183, -15639, -6297, -5426, -4000, 10906, 14453, 11143, 7782, 1920, 1639, -9678, 7663, 8629, -11977, -8645, 15677, -4474, -12419, 3194, 13277, 14492, 12786, 6119, 8392, 13028, -781, 3979, -6223, -12311, -6764, 10545, 12070, -4049, -16644, 11284, -1039, -7131, 5223, -3246, 3598, -7889, 13976, -2378, -2380, -13853, 8187, 4089, 1242, 4828, -3124, -3710, -275, -2820, 1907, 311, -4162, -5868, -1954, -8611, 3431, -9398, -8222, 7923, 10891, 1304, 912, 1053, 3682, -9213, -5074, -5581, 14174, 16721, -4364, 4197, -4400, -6354, -5818, 18182, -621, -5375, -320, 1992, -4238, 14160, -5359, -3867, 3029, 2480, 3646, -12070, -9077, -3823, -11018, -5502, 769, 4380, 1147, -8624, -2222, 5489, 9789, 11221, -3276, 1011, -2549, -8551, -8424, 2819, -3318, 7550, -6432, 1016, 3693, -5294, -6910, 585, 12803, -8481, -519, -3592, 8384, -8061, 1483, -18989, 1715, 5742, -10307, -5863, -3238, 5371, 3, -9345, 1456, -5205, -595, -5478, -338, -3029, -2848, -8291, 7431, -7711, -13980, 2946, -4041, -7821, 13553, 2371, -4586, -14111, -13771, 10576, -5223, 14670, 2638, -864, -13714, 11097, -16911, -17207, 6660, -9715, 12947, 4976, 8732, 6598, 8408, 8937, 6288, 9330, -14852, -185, -99, -10756, 14067, -2739, 4901, -12547, -7070, 7565, -5536, -4078, -7210, -3171, 3512, -9967, 1229, -13539, 14239, 9555, 6835, -5893, -2028, -1489, 7728, -3751, 2798, -15332, -6087, -2637, -4006, -5678, 7804, -8603, -471, -4826, 8264, -11354, 10973, -8683, -160, 5190, 1610, -5779, -8116, -97, 4448, 13252, -6678, -11221, -2083, -7328, 903, -1293, -968, 12563, -10110, 16798, -471, -7530, 11092, 6268, -17975, -4717, 11800, 16380, 744, -4612, 6676, -11572, 1024, -9039, -493, -1127, 7979, 2903, -789, -3655, -1094, -2841, -8759, -5310, -1027, 3672, -15166, -10900, -12577, 2797, -8223, 10218, 10568, -11164, 3629, -5224, -12473, 8884, 4672, -5063, -5519, -348, -8162, -6249, 188, -8923, -5345, 1606, 12834, -145, 2625, 4691, 2556, -853, 2620, -13166, 1816, -7406, -8366, 10665, 2721, 4320, 1594, 761, 14032, -6125, 1681, 4260, 10642, -7377, -11706, 7236, 6909, 531, 18911, 18954, -2206, -12116, 178, -10562, 10601, -2613, -3247, -1436, 15765, -1367, -10700, -5769, 1408, -632, -357, -4311, -2036, -388, -1023, -12154, 7542, -3797, -9834};

        // Calcula o tempo de execução da inserção
        long executionTime = rbTree.calculateExecutionTime(data);

        // Inserção dos dados
        rbTree.insertAll(data);

        // Exibição dos dados organizados
        rbTree.inOrderTraversal();

        // Executa operações aleatórias
        rbTree.performRandomOperations();

        System.out.println("Árvore Rubro-Negra construída em " + executionTime + " nanossegundos.");
    }
}
