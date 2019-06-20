<template>
    <v-card>
        <v-flex xs12 sm10>
            <v-tree url="/item/category/of/parent"
                    isEdit @handleAdd="handleAdd"
                    @handleDelete="handleDelete"
                    @handleEdit="handleEdit"></v-tree>

        </v-flex>

    </v-card>
</template>
<script>

    import * as axios from "axios";
    export default {
        inject:['reload'],
        name: "category",
        data () {
            return {
                isEdit: true
            }
        },
        methods: {
            handleAdd (node) {
                console.log ("add .... ");
                console.log (node.name);
                this.$http.post('http://api.leyou.com/api/item/category/add',{
                    data: {
                        'isParent': node.isParent,
                        'name': node.name,
                        'parentId': node.parentId,
                        'sort': node.sort,
                    }
                }).then ((response) => {

                    // console.log (response)
                });

            },
            handleEdit (id, name) {
                console.log ("edit... id: " + id + ", name: " + name);
                this.$http.post ("/item/category/edit?id=" + id + "&name=" + name);
                this.reload();
            },
            handleDelete (id) {
                console.log ("delete ... " + id);
                // this.$http.post("api.leyou.com/api/item/category/delete?id=" + id);
                this.$http.post ('/item/category/of/delete', {
                    params: {
                        'id': id,
                    }
                }).then ((response) => {
                    this.$message.info('删除成功');
                    this.reload();
                }).catch(
                    this.$message.info('删除失败')
                )

            },
            handleClick (node) {
                console.log (node)
            }
        }
    };
</script>

<style scoped>

</style>
