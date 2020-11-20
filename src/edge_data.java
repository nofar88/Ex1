package ex1.src;

public interface edge_data {



    /**
     * Return the weight (ID) associated with this rib.
     * @return
     */
    public double getWeight();

    /**
     * return the remark (meta data) associated with this rib.
     * @return
     */
    public String getInfo();
    /**
     * Allows changing the remark (meta data) associated with this rib.
     * @param s
     */
    public void setInfo(String s);
    /**
     * Temporal data (aka distance, color, or state)
     * which can be used be algorithms
     * @return
     */
    public double getTag();

    /**
     * Allow setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    public void setTag(double t);

    /**
     * Return the incoming vertex (IS) associated with this rib.
     * @return
     */
    public node_info getInNode();

    /**
     * Return the outgoing vertex (IS) associated with this rib.
     * @return
     */
    public node_info getOutNode();

    /**
     * Allow setting the "InNode" value
     * @param inNode - the new value of the node
     */
    public void setInNode(node_info inNode);

    /**
     * Allow setting the "OutNode" value
     * @param outNode - the new value of the node
     */
    public void setOutNode(node_info outNode);

    /**
     * Allow setting the "weight" value
     * @param weight - the new value of the node weight
     */
    public void setWeight(double weight);


}
