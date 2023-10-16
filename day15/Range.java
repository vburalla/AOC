package day15;

class Range {

    int leftLimit;
    int rightLimit;

    public Range(int leftLimit, int rightLimit) {
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }

    public int getLeftLimit() {
        return leftLimit;
    }

    public int getRightLimit() {
        return rightLimit;
    }

    public boolean isIntersected(Range range) {

        return !((this.rightLimit < range.leftLimit) || (this.leftLimit > range.rightLimit));
    }

    public void mergeIntersected(Range range, Integer leftRestriction, Integer rightRestriction) {

        int left = Math.min(this.leftLimit, range.leftLimit);
        int right = Math.max(this.rightLimit, range.rightLimit);

        this.leftLimit = (leftRestriction != null && left < leftRestriction)? leftRestriction : left;
        this.rightLimit = (rightRestriction != null && right > rightRestriction)? rightRestriction : right;
    }

    public void mergeIntersected(Range range) {

        int left = Math.min(this.leftLimit, range.leftLimit);
        int right = Math.max(this.rightLimit, range.rightLimit);

        this.leftLimit = left;
        this.rightLimit = right;
    }
}