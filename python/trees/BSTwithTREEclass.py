# this BST has an actual BST class
# need to add deletions

class Node:
    def __init__(self, data):
        self.data = data
        self.left = None
        self.right = None

    def get(self):
        return self.data

    def set(self, data):
        self.data


class BST:

    def __init__(self):
        self.root = None

    def set_root(self, data):
        self.root = Node(data)

    def insert(self, data):
        if self.root is None:
            self.set_root(data)
        else:
            self.insert_node(self.root, data)

    def insert_node(self, current, data):
        if data < current.data:
            if current.left is None:
                current.left = Node(data)
            else:
                self.insert_node(current.left, data)
        elif data > current.data:
            if current.right is None:
                current.right = Node(data)
            else:
                self.insert_node(current.right, data)
        else:
            current.right = Node(data)

    def find(self, data):
        return self.find_node(self.root, data)

    def find_node(self, current, data):
        if current is None:
            return False
        elif data == current.data:
            return True
        elif data < current.data:
            return self.find_node(current.left, data)
        else:
            return self.find_node(current.right, data)

    def print_tree(self):
        self.in_order(self.root)

    def in_order(self, data):
        if data.left:
            self.in_order(data.left)
        print(data.data)
        if data.right:
            self.in_order(data.right)


if __name__ == '__main__':
    my_tree = BST()
    my_tree.insert(8)
    my_tree.insert(3)
    my_tree.insert(10)
    my_tree.insert(14)
    my_tree.insert(6)
    my_tree.insert(1)
    my_tree.insert(17)
    my_tree.insert(4)
    my_tree.insert(13)
    my_tree.print_tree()

