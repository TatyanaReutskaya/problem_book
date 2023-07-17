import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static ArrayList<Integer> al = new ArrayList<>();
    public static void main(String[] args) {
        //test some method
    }
    //https://leetcode.com/problems/uncrossed-lines/description/
    public static int maxUncrossedLines(int[] nums1, int[] nums2) {
        int[] ans=new int[nums2.length+1];
        for (int i=1;i<=nums1.length;i++) {
            int[] temp=new int[nums2.length+1];
            for (int k=1;k<=nums2.length;k++) {
                if (nums1[i-1]==nums2[k-1]) {
                    temp[k]=ans[k-1]+1;
                }
                else {
                    temp[k]=Math.max(ans[k],temp[k-1]);
                }
            }
            ans=temp;
        }
        return ans[nums2.length];
    }
    //https://leetcode.com/problems/solving-questions-with-brainpower/description/
    public static long mostPoints(int[][] questions) {
        long[] ans = new long[questions.length+1];
        int prevQ=0;
        int point=0;
        int skip=0;
        for (int i=questions.length-1;i>=0;i--) {
            point=questions[i][0];
            skip=questions[i][1];
            prevQ=Math.min(questions.length,i+skip+1);
            ans[i]=Math.max(ans[i+1],point+ans[prevQ]);
        }
        return ans[0];
    }
    //https://leetcode.com/problems/find-smallest-letter-greater-than-target/
    public static char nextGreatestLetter(char[] letters, char target) {
        if (target<letters[0]) {
            return letters[0];
        }
        int start = 0;
        int end = letters.length-1;
        int med;
        while ((start+1)<end) {
            med=(start+end)/2;

            if (target<letters[med]) {
                end=med;
            }
            else {
                start=med;
            }
        }
        if (target<letters[end]) {
            return letters[end];
        }
        else {
            return letters[0];
        }

    }
    public static int getMinimumDifference(TreeNode root) {
        inList(root);
        Collections.sort(al);
        int ans = Integer.MAX_VALUE;
        for (int i=al.size()-1;i>0;i--) {
            ans=Math.min(ans,(al.get(i)-al.get(i-1)));
        }
        return ans;
    }
    public static void inList(TreeNode root){
        al.add(root.val);
        if(root.left!=null) inList(root.left);
        if(root.right!=null) inList(root.right);
    }
    public static int oneElement(ArrayList<Integer> al) {
        int sum = al.stream().distinct().reduce(Integer::sum).orElseGet(() -> 0);
        return al.stream().reduce(Integer::sum).orElseGet(() -> 0) - sum;
    }
    public static Map<Integer, Integer> manyElements(ArrayList<Integer> al) {
        Map<Integer, Integer> hm = new HashMap<>();
        al.forEach((e) -> hm.put(e, hm.get(e) == null ? 1 : hm.get(e) + 1));
        return hm.entrySet().stream().filter((n) -> n.getValue() != 1).collect(Collectors.toMap((n) -> n.getKey(), (n) -> n.getValue()));
    }
    public int[] getSubarrayBeauty(int[] nums, int k, int x) {
        int[] res = new int[nums.length - k + 1];
        for (int i = 0; i <= nums.length - k; i++) {
            res[i] = Arrays.stream(nums, i, i + k).filter((n) -> n < 0).sorted().skip(x - 1).findFirst().orElseGet(() -> 0);
        }
        return res;
    }
    public static int addMinimum(String word) {
        int[] count = new int[3];
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == 'a') {
                count[0]++;
            } else if (word.charAt(i) == 'b') {
                count[1]++;
            } else {
                count[2]++;
            }
        }
        Arrays.sort(count);
        return ((count[2] - count[1]) + (count[2] - count[0]));

    }
    public static String minWindow(String s, String t) {
        HashMap<Character, Integer> hm = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            Character ch = t.charAt(i);
            hm.put(ch, hm.getOrDefault(ch, 0) + 1);
        }
        int matchCount = hm.size();
        int minSize = Integer.MAX_VALUE;
        int firstIndex = 0;
        int leftIndex = 0;
        for (int i = 0; i < s.length(); i++) {
            Character ch = s.charAt(i);
            if (hm.containsKey(ch)) {
                hm.put(ch, hm.get(ch) - 1);
                if (hm.get(ch) == 0) {
                    matchCount--;
                }
            }
            while ((leftIndex <= i) && (matchCount == 0)) {
                if (minSize > i - leftIndex + 1) {
                    minSize = i - leftIndex + 1;
                    firstIndex = leftIndex;
                }
                Character chLeft = s.charAt(leftIndex);
                if (hm.containsKey(chLeft)) {
                    hm.put(chLeft, hm.get(chLeft) + 1);
                    if (hm.get(chLeft) > 0) {
                        matchCount++;
                    }
                }
                leftIndex++;
            }
        }
        System.out.println(firstIndex + "  " + minSize);
        return (minSize == Integer.MAX_VALUE ? "" : s.substring(firstIndex, minSize + firstIndex));
    }
    public static List<Integer> findSubstring(String s, String[] words) {
        List<Integer> resList = new ArrayList<>();
        HashMap<String, Integer> hm = new HashMap<>();
        for (String key : words) {
            hm.put(key, hm.getOrDefault(key, 0) + 1);
        }
        int matchCount = hm.size();
        int firstIndex = 0;
        int wordsLength = words[0].length();
        for (int i = 0; i <= s.length() - words.length * wordsLength; i++) {
            String key = s.substring(i, i + words.length * wordsLength);
            if (chechWord(key,hm,wordsLength)) {
                resList.add(i);
            }
        }
        return resList;
    }
    public static boolean chechWord (String s,HashMap<String, Integer> hm, int wordLen) {
        HashMap<String, Integer> hmCh = new HashMap<>(hm);
        int match = hmCh.size();
        for (int i=0;i<=s.length()-wordLen;i=i+wordLen) {
            String key=s.substring(i,i+wordLen);
            if (hmCh.containsKey(key)) {
                hmCh.put(key,hm.get(key)-1);
                if (hmCh.get(key)==0) {
                    match--;
                }
            }
        }
        return match==0;
    }
    public static int characterReplacement(String s, int k) {
        HashMap<Character,Integer> hm = new HashMap<>();
        int countChar=0;
        int lastInd = 0;
        int maxLen = Integer.MIN_VALUE;
        int maxHM=0;
        for (int i=0;i<s.length();i++) {

            while ((countChar<=k)&&(lastInd<s.length())) {
                hm.put(s.charAt(lastInd),hm.getOrDefault(s.charAt(lastInd),0)+1); //прибавляем символ
                maxHM = hm.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getValue();//максимальное кольчиство символа
                int lenSub=s.substring(i,lastInd+1).length();

                countChar=lenSub-maxHM;

                if ((maxLen<lastInd-i+1)&&(countChar==k)) {
                    maxLen=lastInd-i+1;
                }
                lastInd++;
            }
            hm.put(s.charAt(i),hm.getOrDefault(s.charAt(i),0)-1); //вычитаем сивол
            countChar--;
        }

        return maxLen==Integer.MIN_VALUE?s.length():maxLen;
    }
    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> resList = new ArrayList<>();
        HashMap<Character,Integer> hmP = new HashMap<>();
        HashMap<Character,Integer> hmS = new HashMap<>();
        for (int i=0;i<p.length();i++) {
            hmP.put(p.charAt(i),hmP.getOrDefault(p.charAt(i),0)+1);
        }
        int firstInd=0;
        int lastInd=p.length()-1;
        for (int i=0;i<s.length();i++) {
            hmS.put(s.charAt(i),hmS.getOrDefault(s.charAt(i),0)+1);
            if (i==lastInd) {
                if (hmS.equals(hmP)) {
                    resList.add(firstInd);
                }
                lastInd++;
                hmS.put(s.charAt(firstInd),hmS.getOrDefault(s.charAt(firstInd),0)-1);
                if (hmS.get(s.charAt(firstInd))==0) {
                    hmS.remove(s.charAt(firstInd));
                }
                firstInd++;
            }
        }
        return resList;
    }
    public static boolean checkInclusion(String s1, String s2) {
        HashMap<Character,Integer> s1HM = new HashMap<>();
        HashMap<Character,Integer> s2HM = new HashMap<>();
        for (int i=0;i<s1.length();i++) {
            s1HM.put(s1.charAt(i),s1HM.getOrDefault(s1.charAt(i),0)+1);
        }
        int firstIndex = 0;
        int lastIndex=s1.length()-1;
        for (int i=0;i<s2.length();i++) {
            s2HM.put(s2.charAt(i),s2HM.getOrDefault(s2.charAt(i),0)+1);
            if (i==lastIndex) {
                if (s2HM.equals(s1HM)) {
                    return true;
                }
                lastIndex++;
                s2HM.put(s2.charAt(firstIndex),s2HM.getOrDefault(s2.charAt(firstIndex),0)-1);
                if (s2HM.get(s2.charAt(firstIndex))==0) {
                    s2HM.remove(s2.charAt(firstIndex));
                }
                firstIndex++;

            }
        }
        return false;
    }
    public static int totalFruit(int[] fruits) {
        HashMap<Integer,Integer> hm = new HashMap<>();
        int maxSum=Integer.MIN_VALUE;
        int firstIndex=0;
        for (int i=0;i<fruits.length;i++) {
            hm.put(fruits[i],hm.getOrDefault(fruits[i],0)+1);
            if ((hm.size()==2)&&(maxSum<i-firstIndex+1)) {
                maxSum=i-firstIndex+1;
            }
            while((hm.size()>2)&&(firstIndex<i)) {
                hm.put(fruits[firstIndex],hm.getOrDefault(fruits[firstIndex],0)-1);
                if (hm.get(fruits[firstIndex])==0) {
                    hm.remove(fruits[firstIndex]);
                }
                firstIndex++;
            }
        }
        return maxSum==Integer.MIN_VALUE?fruits.length:maxSum;
    }
    public static int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int i=0;
        int t;
        int k;
        int first=1;
        while (first<=n*n) {
            t=i;
            k=i;
            while(k<n-i) {
                res[t][k] = first;
                first++;
                k++;
            }
            k--;
            t++;
            while(t<n-i) {
                res[t][k] = first;
                first++;
                t++;
            }
            t--;
            k--;
            while(k>=i) {
                res[t][k] = first;
                first++;
                k--;
            }
            k++;
            t--;
            while(t>=1+i) {
                res[t][k] = first;
                first++;
                t--;
            }
            k++;
            t++;
            i++;

        }
        return res;
    }
    public static int pairSum(ListNode head) {
        ListNode headCh = new ListNode(0,head);
        List<Integer> headL = new ArrayList<Integer>();
        while(headCh.next!=null) {
            headL.add(headCh.next.val);
            headCh = headCh.next;
        }
        System.out.println(headL);
        int n = headL.size();
        int max = Integer.MIN_VALUE;
        int sum;
        for (int i=0; i<n/2;i++) {
            sum = headL.get(i)+headL.get(n-1-i);
            if (max<sum) {
                max=sum;
            }
        }
        return max;
    }
}
