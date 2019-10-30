class Node:
    def __init__(self, data):
        self.left = None
        self.right = None
        self.data = data

    def insert(self, data):
        if data < self.data:
            if self.left is None:
                self.left = Node(data)
            else:
                self.left.insert(data)
        elif data > self.data:
            if self.right is None:
                self.right = Node(data)
            else:
                self.right.insert(data)
        else:
            self.data = data

    def inquire(self, data, parent=None):
        if data < self.data:
            if self.left is None:
                return None, None
            return self.left.inquire(data, self)
        elif data > self.data:
            if self.right is None:
                return None, None
            return self.right.inquire(data, self)
        else:
            return self, parent

    def children_count(self):
        count = 0
        if self.left:
            count += 1
        if self.right:
            count += 1
        return count

    def delete(self, data):
        node, parent = self.inquire(data)
        if node is not None:
            children_count = node.children_count()
        if children_count == 0:
            if parent:
                if parent.left is node:
                    parent.left = None
                else:
                    parent.right = None
                del node
            else:
                self.data = None
        elif children_count == 1:
            if node.left:
                n = node.left
            else:
                n = node.right
            if parent:
                if parent.left is node:
                    parent.left = n
                else:
                    parent.right = n
                del node
            else:
                self.left = n.left
                self.right = n.right
                self.data = n.data
        else:
            parent = node
            nxt = node.right
            while nxt.left:
                parent = nxt
                nxt = nxt.left
            node.data = nxt.data
            if parent.left == nxt:
                parent.left = nxt.left
            else:
                parent.right = nxt.right

    def print_tree(self):
        if self.left:
            self.left.print_tree()
        print(self.data)
        if self.right:
            self.right.print_tree()


if __name__ == '__main__':
    my_tree = Node(8)
    my_tree.insert(3)
    my_tree.insert(10)
    my_tree.insert(14)
    my_tree.insert(6)
    my_tree.insert(1)
    my_tree.insert(17)
    my_tree.insert(4)
    my_tree.insert(13)

    my_tree.print_tree()
