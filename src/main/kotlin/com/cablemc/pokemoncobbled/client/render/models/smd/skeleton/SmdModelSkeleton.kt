package com.cablemc.pokemoncobbled.client.render.models.smd.skeleton

import com.cablemc.pokemoncobbled.client.render.models.smd.mesh.SmdMesh

/**
 * A skeleton for a [SmdModel], storing all bones for the model.
 *
 * @property bones all of the bones within the skeleton
 * @property boneById a map of unique bone id to bone within the skeleton for quick lookup
 *                    key is bone id, value is bone instance
 * @property boneByName a map of bone name to bone within the skeleton for quick lookup
 *                      key is bone name, value is bone instance
 * @property root the root, top-level bone of the skeleton
 * @property mesh the polygon mesh that is applied to the skeleton
 *
 * @author landonjw
 */
class SmdModelSkeleton(
    val bones: List<SmdModelBone>,
    val mesh: SmdMesh
) {

    val root: SmdModelBone = findRoot()

    val boneById: Map<Int, SmdModelBone> = bones.map { bone -> bone.id to bone }.toMap()
    val boneByName: Map<String, SmdModelBone> = bones.map { bone -> bone.name to bone }.toMap()

    init {
        bones.forEach { bone ->
            mesh.verticesByBone[bone.id]?.forEach { vertex -> bone.addVertex(vertex) }
        }
        linkChildren()
        root.adjust()
        bones.forEach { it.invertBaseTransformation() }
    }

    private fun linkChildren() {
        bones.forEach { bone -> bone.parent?._children?.add(bone) }
    }

    private fun findRoot(): SmdModelBone {
        // Searches for a top-level bone that has children.
        for (bone in bones) {
            if (bone.parent == null && bone.children.isNotEmpty()) {
                return bone
            }
        }
        // If the first iteration fails, just get any bone that isn't meaningless bone generated by blender
        return bones.firstOrNull { it.name != "blender_implicit" } ?: throw IllegalStateException("no root bone found")
    }

    /** Resets the skeleton, causing the model to t-pose. */
    fun reset() {
        mesh.reset()
    }

}